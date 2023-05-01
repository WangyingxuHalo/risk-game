package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The AccountService class that allows user login and registration
 */
@Service
public class ViewService {
    /**
     * Spy repository.
     */
    @Autowired
    private SpyRepository spyRepository;
    /**
     * Territory Repository
     */
    @Autowired
    private TerritoryRepository territoryRepository;

    /**
     * Repository of Units
     */
    @Autowired
    private UnitRepository unitRepository;
    /**
     * Repository of territory views.
     */
    @Autowired
    private TerritoryViewRepository territoryViewRepository;
    /**
     * Repository of unit views.
     */
    @Autowired
    private UnitViewRepository  unitViewRepository;
    /**
     * Neighbor repository
     */
    @Autowired
    private NeighborRepository neighborRepository;
    /**
     * Player repository.
     */
    @Autowired
    private PlayerRepository playerRepository;

    public void updateAllViews(int gameId) {
        List<Player> players = playerRepository.findByGameId(gameId);
        for (Player p : players) {
            updateViews(p.getId());
        }
    }

    /**
     * Update the views for a player based on the current state of the board.
     * @param playerID is the player to update for.
     */
    public void updateViews(int playerID) {
        Set<Integer> tIDs = new HashSet<>();
        List<Territory> terrs = territoryRepository.findByOwnerId(playerID);
        for (Territory t : terrs) {
            tIDs.add(t.getId());
        }
        updateAdjacencies(terrs, tIDs, playerID);
        updateSpies(playerID, tIDs);
    }

    /**
     * Update the views for all territories that have this players spies in them.
     * @param playerID is the unique player ID.
     * @param tIDs is the set of territories this player owns.
     */
    protected void updateSpies(int playerID, Set<Integer> tIDs) {
        List<Spy> spies = spyRepository.findByPlayerId(playerID);
        for (Spy spy : spies) {
            int tID = spy.getTerritoryId();
            if (!tIDs.contains(tID)) {
                Territory t = territoryRepository.findById(tID).get();
                updateOneView(t, playerID);
            }
        }
    }

    /**
     * Update adjacent territories.
     * @param territories the territories owned by the player
     * @param tIDs Set of all territory IDs owned by player.
     * @param playerId player identifier.
     */
    protected void updateAdjacencies(List<Territory> territories, Set<Integer> tIDs, int playerId) {
        for (Territory t : territories) {
            List<Neighbor> neighbors = neighborRepository.findByNeighborId(t.getId());
            for (Neighbor n : neighbors) {
                Territory nT = territoryRepository.findById(n.getTerritoryId()).get();
                if (!tIDs.contains(n.getTerritoryId()) && nT.getCloak() == 0) {
                    updateOneView(nT, playerId);
                }
            }
        }
    }

    /**
     * Update the view of one territory.
     * @param nT is the territory to update.
     * @param playerId is the player identifier.
     */
    protected void updateOneView(Territory nT, int playerId) {
        int tID = nT.getId();
        int owner = nT.getOwnerId();
        List<Unit> units = unitRepository.findByTerritoryId(tID);
        Optional<TerritoryView> optTView = territoryViewRepository.findByTerritoryIdAndPlayerId(tID,
                playerId);
        if (optTView.isPresent()) {
            TerritoryView tView = optTView.get();
            int tViewID = tView.getId();
            for (Unit u: units) {
                UnitView uView = unitViewRepository.findByTerritoryViewIdAndType(tViewID, u.getType());
                uView.setCount(u.getCount());
                unitViewRepository.save(uView);
            }
            tView.setOwnerId(owner);
            territoryViewRepository.save(tView);
        }
        else {
            int tViewId = territoryViewRepository.save(new TerritoryView(tID, owner, playerId)).getId();
            for (Unit u: units) {
                unitViewRepository.save(new UnitView(u.getType(), u.getCount(), tViewId));
            }
        }
    }
}
