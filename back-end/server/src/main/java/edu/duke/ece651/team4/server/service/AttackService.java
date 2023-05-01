package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.model.TwentySidedDice;
import edu.duke.ece651.team4.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The service to conduct Attacks.
 */
@Service
public class AttackService {
    /**
     * Service to finish a turn.
     */
    @Autowired
    FinishTurnService finishTurnService;
    /**
     * Repository containing the resolved attacks.
     */
    @Autowired
    ResolveRepository resolveRepository;
    /**
     * Repository containing the details of resolved attacks.
     */
    @Autowired
    ResolveDetailRepository resolveDetailRepository;

    /**
     * Repository containing all units.
     */
    @Autowired
    UnitRepository unitRepository;

    /**
     * Repository containing the attacks.
     */
    @Autowired
    AttackRepository attackRepository;

    /**
     * Territory repository
     */
    @Autowired
    TerritoryRepository territoryRepository;

    /**
     * Repository of all player orders.
     */
    @Autowired
    PlayerOrderRepository playerOrderRepository;
    /**
     * Game repository.
     */
    @Autowired
    GameRepository gameRepository;

    /**
     * Dice to roll in attack.
     */
    TwentySidedDice dice = new TwentySidedDice();


    /**
     * Check if all players have submitted orders. If so, conduct attack.
     *
     * @param gameID is the Game.
     * @return boolean: true if attacks were conducted, false otherwise.
     */
    public boolean conductAttack(int gameID) {
        Game game = gameRepository.findById(gameID).get();
        int numPlayers = game.getNumAlivePlayers();
        List<PlayerOrder> orders = playerOrderRepository.findByGameID(gameID);
        if (orders.size() == numPlayers) {
            List<Resolve> resolves = resolveRepository.findByGameId(gameID);
            for (Resolve r : resolves) {
                resolveDetailRepository.deleteByResolveId(r.getId());
                resolveRepository.delete(r);
            }
            resolveAttack(gameID, orders);
            playerOrderRepository.deleteByGameID(gameID);
            finishTurnService.finishTurn(gameID);
            return true;
        }
        return false;
    }

    /**
     * Called if all players have commited their moves for this round. Conducts and resolves attacks.
     *
     * @param gameID is the gameID for this game.
     * @param orders is the list of all player orders.
     */
    protected void resolveAttack(int gameID, List<PlayerOrder> orders) {
        HashMap<Integer, HashMap<Integer, List<Integer>>> attackMap = buildAttackMap(orders);
        sortAttackMap(attackMap);
        for (Integer destID : attackMap.keySet()) {
            HashMap<Integer, List<Integer>> attackingForces = attackMap.get(destID);
            HashMap<Integer, Integer> bonuses = getBonuses();
            Integer owner = conductOneAttack(attackingForces, bonuses);
            Resolve resolve = resolveRepository.findByAttackedTerritoryId(destID).get();
            resolve.setWinnerId(owner);
            resolveRepository.save(resolve);
            Territory t = territoryRepository.findById(destID).get();
            t.setOwnerId(owner);
            List<Integer> remainingUnits = attackingForces.get(owner);
            for (Integer u : remainingUnits) {
                Unit toUpdate = unitRepository.findByTerritoryIdAndType(destID, u);
                toUpdate.setCount(toUpdate.getCount() + 1);
                unitRepository.save(toUpdate);
            }
        }
    }

    /**
     * Get the specific roll bonuses.
     * @return HashMap of bonsuses by integer level.
     */
    protected HashMap<Integer, Integer> getBonuses() {
        HashMap<Integer, Integer> bonuses= new HashMap<Integer, Integer>();
        bonuses.put(0,0);
        bonuses.put(1,1);
        bonuses.put(2,3);
        bonuses.put(3, 5);
        bonuses.put(4, 8);
        bonuses.put(5, 11);
        bonuses.put(6, 15);
        return bonuses;
    }

    /**
     * Build the map of all attackers, by destination and player ID
     * @param orders are all the player orders.
     * @return HashMap containing all attacks to resolve.
     */
    protected HashMap<Integer,
            HashMap<Integer, List<Integer>>> buildAttackMap(List<PlayerOrder> orders) {
        HashMap<Integer, HashMap<Integer, List<Integer>>> attackMap = new HashMap<>();
        for (PlayerOrder pOrder : orders) {
            int playerID = pOrder.getPlayerId();
            List<Attack> attacks = attackRepository.findByOrderId(pOrder.getId());
            for (Attack a : attacks) {
                saveAttack(a, pOrder.getGameID());
                int destID = a.getDestId();
                if (attackMap.containsKey(destID)) {
                    HashMap<Integer, List<Integer>> attackByPlayer = attackMap.get(destID);
                    if (attackByPlayer.containsKey(playerID)) {
                        List<Integer> units = attackByPlayer.get(playerID);
                        units.add(a.getUnitStrength());
                    } else {
                        List<Integer> units = new ArrayList<>();
                        units.add(a.getUnitStrength());
                        attackByPlayer.put(playerID, units);
                    }
                } else {
                    List<Integer> units = new ArrayList<>();
                    HashMap<Integer, List<Integer>> attackByPlayer = new HashMap<>();
                    units.add(a.getUnitStrength());
                    List<Integer> unitsToDefend = new ArrayList<>();
                    List<Unit> defendUnits = unitRepository.findByTerritoryId(destID);
                    for (Unit d : defendUnits) {
                        for (int i = 0; i < d.getCount(); i++) {
                            unitsToDefend.add(d.getType());
                        }
                        d.setCount(0);
                        unitRepository.save(d);
                    }
                    Territory t = territoryRepository.findById(destID).get();
                    if (unitsToDefend.size() > 0) {
                        attackByPlayer.put(t.getOwnerId(), unitsToDefend);
                    }
                    attackByPlayer.put(playerID, units);
                    attackMap.put(destID, attackByPlayer);
                }
                attackRepository.deleteById(a.getId());
            }
        }
        return attackMap;
    }

    /**
     * Conduct one attack on a single territory.
     * @param attackingForces are the attacking and defending forces.
     * @return the ID of the winning player.
     */
    protected Integer conductOneAttack(HashMap<Integer, List<Integer>> attackingForces,
                                       HashMap<Integer, Integer> bonuses) {
        while (attackingForces.size() > 1) {
            List<Integer> attackerList = new ArrayList<>(attackingForces.keySet());
            int N = attackerList.size();
            for (int i = 0; i < N; i++) {
                List<Integer> attacker1 = attackingForces.get(attackerList.get(i));
                List<Integer> attacker2 = attackingForces.get(attackerList.get((i +1) % N));
                fight(attacker1, attacker2, bonuses);
                if (attacker1.size() == 0) {
                    attackingForces.remove(attackerList.get(i));
                    break;
                }
                if (attacker2.size() == 0) {
                    attackingForces.remove(attackerList.get((i +1) % N));
                    break;
                }
            }
        }
        Integer ans = 0;
        for (Integer alive : attackingForces.keySet()) {
            ans = alive;
        }
        return ans;
    }

    /**
     * Fight between two attackers.
     * @param attacker1 first attacker.
     * @param attacker2 second attacker.
     * @param bonuses the bonuses for each unit strength.
     */
    protected void fight(List<Integer> attacker1, List<Integer> attacker2,
                         HashMap<Integer, Integer> bonuses) {
        Integer attackUnit1 = attacker1.get(0);
        Integer attackUnit2 = attacker2.get(attacker2.size() - 1);
        int attack1Roll = dice.roll() + bonuses.get(attackUnit1);
        int attack2Roll = dice.roll() + bonuses.get(attackUnit2);
        if (attack1Roll > attack2Roll) {
            attacker2.remove(attacker2.size() - 1);
        }
        else {
            attacker1.remove(0);
        }
    }

    /**
     * Sort all the lists of units in the attack map.
     * @param attackMap is the attack map to sort.
     */
    protected void sortAttackMap(HashMap<Integer, HashMap<Integer, List<Integer>>> attackMap) {
        for (Integer destID : attackMap.keySet()) {
            HashMap<Integer, List<Integer>> forces = attackMap.get(destID);
            for (Integer playerID : forces.keySet()) {
                List<Integer> units = forces.get(playerID);
                Collections.sort(units);
            }
        }
    }

    /**
     * Save a "resolved" attack to the database.
     * @param a is the attack currently conducted.
     * @param gameID is the game ID.
     */
    protected void saveAttack(Attack a, int gameID) {
        int destID = a.getDestId();
        int srcID = a.getSrcId();
        int attackerID = a.getPlayerId();
        Optional<Resolve> optResolve = resolveRepository.findByAttackedTerritoryId(destID);
        if (optResolve.isPresent()){
            int resolveID = optResolve.get().getId();
            List<ResolveDetail> details = resolveDetailRepository.findByResolveId(resolveID);
            boolean contains = false;
            for (ResolveDetail detail : details) {
                if (detail.getTerritoryId() == srcID) {
                    contains = true;
                }
            }
            if (!contains) {
                ResolveDetail detail = new ResolveDetail(attackerID, srcID, resolveID);
                resolveDetailRepository.save(detail);
            }
        }
        else {
            int ownerID = territoryRepository.findById(destID).get().getOwnerId();
            Resolve resolution = new Resolve(ownerID, ownerID, destID, gameID);
            int resolveID = resolveRepository.save(resolution).getId();
            ResolveDetail detail = new ResolveDetail(attackerID, srcID, resolveID);
            resolveDetailRepository.save(detail);
        }
    }

}