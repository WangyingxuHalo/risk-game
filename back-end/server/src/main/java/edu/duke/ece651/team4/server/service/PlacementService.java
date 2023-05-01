package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.Player;
import edu.duke.ece651.team4.server.entity.Territory;
import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.model.Placement;
import edu.duke.ece651.team4.server.model.UserPlacement;
import edu.duke.ece651.team4.server.repository.PlayerRepository;
import edu.duke.ece651.team4.server.repository.TerritoryRepository;
import edu.duke.ece651.team4.server.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The PlacementService class for unit placements
 */
@Service
public class PlacementService {

    /**
     * Territory repository to pull from.
     */
    @Autowired
    private TerritoryRepository territoryRepository;

    /**
     * Unit repository to pull unit info
     */
    @Autowired
    private UnitRepository unitRepository;

    /**
     * Player repository to fetch player info.
     */
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * method to do user placement for one user.
     *
     * @param userPlacement the user placement
     */
    public void doUnitsPlacement(UserPlacement userPlacement) {
        int pID = userPlacement.getPlayerId();
        for (Placement p : userPlacement.getPlacements()) {
            Territory t = territoryRepository.findByNameAndOwnerId(p.getTerrName(), pID);
            unitRepository.save(new Unit(0, p.getNumUnits(), t.getId()));
            for (int type = 1; type < 7; type++) {
                unitRepository.save(new Unit(type, 0, t.getId()));
            }
        }
        Player player = playerRepository.findById(pID).get();
        player.setPlacementDone(true);
        playerRepository.save(player);
    }

    /**
     * Method to check if placement is done for all players
     *
     * @param gameID is the game id
     * @return Boolean true if placement is done for all players else false
     */
    public Boolean isPlacementDone(int gameID) {
        List<Player> players = playerRepository.findByGameId(gameID);
        boolean isPlacementDone = true;
        for (Player player : players) {
            if (!player.isPlacementDone()) {
                isPlacementDone = false;
                break;
            }
        }
        return isPlacementDone;
    }
}
