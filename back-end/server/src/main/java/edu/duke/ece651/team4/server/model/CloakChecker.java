package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;

import java.util.HashMap;
import java.util.List;

/**
 * Check that all moves are valid.
 */
public class CloakChecker extends ActionChecker {
    public CloakChecker(ActionChecker next) {
        super(next);
    }

    /**
     * Checks that the player have enough tech to Cloack the terrotery, assume used 25 technology to colse 3 turn.
     *
     * @param units           is the units in each territories.
     * @param neighbors       is the adjacency list.
     * @param turn            is the turn object representing the player moves.
     * @param playerResources resources currently available.
     * @return
     */
    @Override
    protected void checkMyRule(HashMap<String, List<Unit>> units,
                               HashMap<String, HashMap<String, Integer>> neighbors,
                               OnePlayerTurn turn,
                               Resources playerResources) {
        if (turn.getClocking().size() > 0) {
            if (turn.getTechLevel() < 3) {
                throw new IllegalArgumentException("You do not have enough technology level to cloak.");

            }
            int cost = 50;
            playerResources.consumeTechnology(turn.getClocking().size() * cost);
        }
    }
}


