package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;

import java.util.HashMap;
import java.util.List;


/**
 * Abstract class representing action checking chain for player turn.
 */
public abstract class ActionChecker {
    /**
     * Next rule checker in chain.
     */
    private final ActionChecker next;

    /**
     * Public constructor, taking next checker (null if end of chain).
     * @param next is the next rule checker.
     */
    public ActionChecker(ActionChecker next) {
        this.next = next;
    }

    /**
     * Checks for legal moves.
     * @param units is the units in each territories.
     * @param neighbors is the adjacency list.
     * @param turn is the turn object representing the player moves.
     * @param playerResources are the food and technology resources currently available.
     */
    protected abstract void checkMyRule(HashMap<String, List<Unit>> units,
                                       HashMap<String, HashMap<String, Integer>> neighbors,
                                       OnePlayerTurn turn,
                                       Resources playerResources);

    /**
     * Chains together all rule checkers, calling them until none left.
     * @param units Are the units in each territories.
     * @param neighbors territory adjacency list.
     * @param turn the object representing the players turn.
     * @param playerResources are the food and technology resources currently available.
     */
    public void checkTurn(HashMap<String, List<Unit>> units,
                         HashMap<String, HashMap<String, Integer>> neighbors,
                         OnePlayerTurn turn,
                         Resources playerResources) {
        checkMyRule(units, neighbors, turn, playerResources);
        if (next != null) {
            next.checkTurn(units, neighbors, turn, playerResources);
        }
    }
}
