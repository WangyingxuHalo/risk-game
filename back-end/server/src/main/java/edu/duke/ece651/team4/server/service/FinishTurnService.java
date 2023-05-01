package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.round;

@Service
public class FinishTurnService {
    @Autowired
    ViewService viewService;

    @Autowired
    SeasonService seasonService;
    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TerritoryRepository territoryRepository;

    /**
     * Finish a turn by assuring players are alive, assuming game is continuing, and adding generation.
     * @param gameID
     */
    public void finishTurn(int gameID) {
        Game game = gameRepository.findById(gameID).get();
        List<Player> players = playerRepository.findByGameId(gameID);
        resourceGeneration(players, gameID);
        int numPlayers = 0;
        for (Player p : players) {
            if (p.getAlive()) {
                numPlayers += 1;
            }
        }
        if (numPlayers == 1) {
            game.setGameOver(true);
        }
        game.setNumAlivePlayers(numPlayers);
        seasonService.checkAndUpdateSeason(gameID, game.getTurnNum() + 1);
        game.setTurnNum(game.getTurnNum() + 1);
        gameRepository.save(game);
    }

    /**
     * Generate resources for all players, both tech and food.
     *
     * @param players list of all players to generate resources for.
     * @param gameID
     */
    protected void resourceGeneration(List<Player> players, int gameID) {
        Season s = seasonRepository.findByGameId(gameID);
        double foodAdjust = s.getFoodAdjust();
        double techAdjust = s.getTechAdjust();
        for (Player p : players) {
            List<Territory> territories = territoryRepository.findByOwnerId(p.getId());
            if (territories.size() == 0) {
                p.setAlive(false);
            }
            double foodResource = p.getTotalFood();
            double techResource = p.getTotalTech();
            for (Territory t : territories) {
                foodResource += t.getFoodGeneration() * foodAdjust;
                techResource += t.getTechGeneration() * techAdjust;
                addBasicUnit(t.getId());
            }
            p.setTotalFood((int) round(foodResource));
            p.setTotalTech((int) round(techResource));
            playerRepository.save(p);
            viewService.updateViews(p.getId());
        }
    }

    /**
     * Add a basic unit to a territory.
     * @param tID territory ID to add to.
     */
    protected void addBasicUnit(int tID) {
        Unit u = unitRepository.findByTerritoryIdAndType(tID, 0);
        u.setCount(u.getCount() + 1);
    }
}
