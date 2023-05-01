package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.Season;
import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.model.*;
import edu.duke.ece651.team4.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

/**
 * Provides action service upon submission of a player's turn.
 */
@Service
public class ActionService {
    /**
     * Spy repository.
     */
    @Autowired
    private SpyRepository spyRepository;
    /**
     * Repository containing season information.
     */
    @Autowired
    private SeasonRepository seasonRepository;
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * The service to conduct Attacks.
     */
    @Autowired
    private AttackService attackService;

    /**
     * Action checker.
     */
    private ActionChecker actionChecker = new CloakChecker(new ResearchChecker(new UpgradeChecker
            (new SpyChecker(new MoveChecker(new AttackChecker(null))))));
    /**
     * Player repository.
     */
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Territory repository.
     */
    @Autowired
    private TerritoryRepository territoryRepository;
    /**
     * Unit repository.
     */


    @Autowired
    private UnitRepository unitRepository;
    /**
     * Territory Neighbor repository.
     */
    @Autowired
    private NeighborRepository neighborRepository;
    /**
     * Player Order Repository
     */
    @Autowired
    private PlayerOrderRepository playerOrderRepository;
    /**
     * Attack Repository
     */
    @Autowired
    private AttackRepository attackRepository;

    /**
     * Verify the moves in a players turn.
     *
     * @param turn is the turn object to commit.
     * @return String describing issue with turn, otherwise null.
     */
    public String conductTurn(OnePlayerTurn turn) {
        int playerID = turn.getPlayerID();
        HashMap<String, List<Unit>> units = getUnits(playerID);
        HashMap<String, HashMap<String, Integer>> neighbors = getNeighbors(playerID);
        Player p = playerRepository.findById(playerID).get();
        int gameId = p.getGameId();
        Season s = seasonRepository.findByGameId(gameId);
        Resources playerResources = new Resources(p.getTotalFood(), p.getTotalTech(),
                s.getMoveAdjust(), s.getAttackAdjust());
        try {
            actionChecker.checkTurn(units, neighbors, turn, playerResources);
            checkSpies(turn, gameId, playerResources);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } p.setTotalFood(playerResources.getFoodResources());
        removeCloak(playerID);
        executeCloak(turn.getClocking(), playerID);
        p.setTotalTech(playerResources.getTechnologyResources());
        playerRepository.save(p);

        // add it here for upgrade

        if (turn.getResearch()) {
            executeResearch(turn.getTechLevel(), playerID);

        }
        executeSpyMoves(turn.getSpyMove(), playerID, gameId);
        executeUpdate(turn.getUpgrade(), playerID);
        executeSpyUpgrade(turn.getSpyUpgrade(), playerID);
        executeMoves(turn.getMove(), playerID);
        saveAttacks(turn, p);
        boolean attacksCompleted = attackService.conductAttack(p.getGameId());
        return null;
    }

    /**
     * Execute the spy moves.
     *
     * @param spyMove is the list of spy moves.
     * @param playerID is the playerID of the player to conduct the moves for.
     */
    protected void executeSpyMoves(List<SpyMove> spyMove, int playerID, int gameID) {
        for (SpyMove move : spyMove) {
            int srcID = territoryRepository.findByNameAndGameId(move.getSrc(), gameID).getId();
            int destID = territoryRepository.findByNameAndGameId(move.getDes(), gameID).getId();
            List<Spy> spies = spyRepository.findByPlayerIdAndTerritoryId(playerID, srcID);
            Spy spy = spies.get(0);
            spy.setTerritoryId(destID);
            spyRepository.save(spy);
        }
    }

    /**
     * Moving Spies is a pain
     */
    protected void checkSpyMove(SpyMove move, int gameID, Resources playerResources) {
        Territory src = territoryRepository.findByNameAndGameId(move.getSrc(), gameID);
        Territory dest = territoryRepository.findByNameAndGameId(move.getDes(), gameID);
        int destID = dest.getId();
        boolean flag = false;
        List<Neighbor> neighbors = neighborRepository.findByNeighborId(
                src.getId());
        for (Neighbor n : neighbors) {
            if (n.getTerritoryId() == destID) {
                flag = true;
                playerResources.move(n.getDistance(), 1);
            }
        }
        if (flag == false) {
            throw new IllegalArgumentException("Spy moves must be from adjacent territories." +
                    " Territories " + src.getName() + " and " + dest.getName() + " are not adjacent.");
        }

    }


    /**
     * Checks if all spies are legal.
     *
     * @param turn   is the Turn Object containing the players moves.
     * @param gameId is the game for which to conduct turn.
     */
    protected void checkSpies(OnePlayerTurn turn, int gameId, Resources playerResources) {
        int id = turn.getPlayerID();
        HashMap<String, Integer> spiesByTerr = new HashMap<>();
        for (SpyMove move : turn.getSpyMove()) {
            checkSpyMove(move, gameId, playerResources);
            String src = move.getSrc();
            if (spiesByTerr.containsKey(src)) {
                spiesByTerr.put(src, spiesByTerr.get(src) + 1);
            } else {
                spiesByTerr.put(src, 1);
            }
        }
        for (String terrName : spiesByTerr.keySet()) {
            int tId = territoryRepository.findByNameAndGameId(terrName, gameId).getId();
            List<Spy> spies = spyRepository.findByPlayerIdAndTerritoryId(id, tId);
            if (spies.size() < spiesByTerr.get(terrName)) {
                throw new IllegalArgumentException("There are not enough spies in territory "
                        + terrName + ".");
            }
        }
    }

    /**
     * Execute the spy upgrades.
     *
     * @param spyUpgrades upgrades to execute.
     * @param playerID    player for which to ugprade.
     */
    protected void executeSpyUpgrade(List<SpyUpgrade> spyUpgrades, int playerID) {
        for (SpyUpgrade upgrade : spyUpgrades) {
            String terrName = upgrade.getTerrName();
            int numSpies = upgrade.getNum();
            int tID = territoryRepository.findByNameAndOwnerId(terrName, playerID).getId();
            Unit u = unitRepository.findByTerritoryIdAndType(tID, 1);
            u.setCount(u.getCount() - numSpies);
            unitRepository.save(u);
            for (int i = 0; i < numSpies; i++) {
                spyRepository.save(new Spy(playerID, tID));
            }
        }
    }

    /**
     * This returns the current state of units in territories at the start of the
     * turn.
     *
     * @param playerID is the playerID for the player who is conducting the actions.
     * @return Hashmap containing each territory and that territories units at the
     * start of the
     * turn.
     */
    @Transactional
    protected HashMap<String, List<Unit>> getUnits(int playerID) {
        HashMap<String, List<Unit>> unitsByTerritory = new HashMap<String, List<Unit>>();
        List<Territory> territories = territoryRepository.findByOwnerId(playerID);
        for (Territory t : territories) {
            List<Unit> units = unitRepository.findByTerritoryId(t.getId());
            for (Unit u : units) {
                entityManager.detach(u);
            }
            unitsByTerritory.put(t.getName(), units);
        }
        return unitsByTerritory;
    }

    /**
     * Get adjacency list representing all territories, neighbors, and distances for
     * this player.
     *
     * @param playerID is the playerID for the player commiting the moves.
     * @return HashMap of territories to their neighbors and distances to those
     * neighbors.
     */
    protected HashMap<String, HashMap<String, Integer>> getNeighbors(int playerID) {
        HashMap<String, HashMap<String, Integer>> neighbors = new HashMap<>();
        List<Territory> territories = territoryRepository.findByOwnerId(playerID);
        for (Territory t : territories) {
            List<Neighbor> ns = neighborRepository.findByNeighborId(t.getId());
            HashMap<String, Integer> tempNeighbors = new HashMap<>();
            for (Neighbor n : ns) {
                String tName = territoryRepository.findById(n.getTerritoryId()).get().getName();
                int distance = n.getDistance();
                tempNeighbors.put(tName, distance);
            }
            neighbors.put(t.getName(), tempNeighbors);
        }
        return neighbors;
    }

    /**
     * execute research in an order.
     *
     * @param techLevel the techlebel to execute.
     * @param playerID  is the playerID for moves to execute.
     */
    protected void executeResearch(int techLevel, int playerID) {
        Player p = playerRepository.findById(playerID).get();
        p.setTechLevel(techLevel + 1);
        playerRepository.save(p);
    }

    /**
     * execute all upgrate in an order.
     *
     * @param upgrades are the upgrate to execute.
     * @param playerID is the playerID for moves to execute.
     */
    protected void executeUpdate(List<Upgrade> upgrades, int playerID) {
        for (Upgrade p : upgrades) {
            int tID = territoryRepository.findByNameAndOwnerId(p.getTerrName(), playerID).getId();
            Unit beforeUnit = unitRepository.findByTerritoryIdAndType(tID, p.getBefore());
            beforeUnit.setCount(beforeUnit.getCount() - p.getNum());
            unitRepository.save(beforeUnit);
            Unit afterUnit = unitRepository.findByTerritoryIdAndType(tID, p.getAfter());
            afterUnit.setCount(afterUnit.getCount() + p.getNum());
            unitRepository.save(afterUnit);
        }
    }

    /**
     * Execute all moves in an order.
     *
     * @param moves    are the moves to execute.
     * @param playerID is the playerID for moves to execute.
     */
    protected void executeMoves(List<Order> moves, int playerID) {
        for (Order m : moves) {
            String dest = m.getDes();
            String src = m.getSrc();
            int srcTID = territoryRepository.findByNameAndOwnerId(src, playerID).getId();
            int destTID = territoryRepository.findByNameAndOwnerId(dest, playerID).getId();
            for (edu.duke.ece651.team4.server.model.Unit u : m.getUnits()) {
                int level = u.getLevel();
                Unit uSrc = unitRepository.findByTerritoryIdAndType(srcTID, level);
                uSrc.setCount(uSrc.getCount() - u.getNum());
                unitRepository.save(uSrc);
                Unit uDst = unitRepository.findByTerritoryIdAndType(destTID, level);
                uDst.setCount(uDst.getCount() + u.getNum());
                unitRepository.save(uDst);
            }
        }
    }


    /**
     * execute all cloak in an order.
     *
     * @param terr are the cloak to execute.
     * @param playerID is the playerID for moves to execute.
     */
    protected void executeCloak(List<String> terr, int playerID) {
        for (String name : terr) {
            Territory t = territoryRepository.findByNameAndOwnerId(name, playerID);
            t.setCloak(t.getCloak() + 3);
            territoryRepository.save(t);
        }
    }

    /**
     * Remove one at the begining of the turn.
     *
     * @param playerID territory ID to add to.
     */
    protected void removeCloak(int playerID) {

        List<Territory> terrs = territoryRepository.findByOwnerId(playerID);
        for (Territory t : terrs) {
            if (t.getCloak() > 0) {
                t.setCloak(t.getCloak() - 1);
                territoryRepository.save(t);
            }
        }

    }

    /**
     * Save all the attacks to attackRepository for later execution.
     *
     * @param turn   is the turn object containing the attacks.
     * @param player is the player executing the attacks.
     */
    protected void saveAttacks(OnePlayerTurn turn, Player player) {
        int playerID = player.getId();
        int gameID = player.getGameId();
        int orderID = playerOrderRepository.save(new PlayerOrder(0, playerID, turn.getResearch(), gameID)).getId();
        List<Order> attacks = turn.getAttack();
        for (Order attack : attacks) {
            int srcID = territoryRepository.findByNameAndOwnerId(attack.getSrc(), playerID).getId();
            int destID = territoryRepository.findByNameAndGameId(attack.getDes(), gameID).getId();
            List<edu.duke.ece651.team4.server.model.Unit> units = attack.getUnits();
            for (edu.duke.ece651.team4.server.model.Unit u : units) {
                int level = u.getLevel();
                int numToAttack = u.getNum();
                Unit toReduce = unitRepository.findByTerritoryIdAndType(srcID, level);
                toReduce.setCount(toReduce.getCount() - numToAttack);
                for (int uNum = 0; uNum < numToAttack; uNum++) {
                    attackRepository.save(new Attack(srcID, destID, orderID, level, playerID));
                }
            }
        }
    }
}
