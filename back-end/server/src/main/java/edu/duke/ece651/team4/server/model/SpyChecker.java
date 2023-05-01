package edu.duke.ece651.team4.server.model;


import edu.duke.ece651.team4.server.entity.Unit;

import java.util.HashMap;
import java.util.List;

public class SpyChecker extends ActionChecker {

    /**
     * Public constructor.
     * @param next is the next checker in the chain, null if otherwise.
     */
    public SpyChecker(ActionChecker next) {
        super(next);
    }

    /**
     * Checks for valid attack.
     *
     * @param units           is the units in each territories.
     * @param neighbors       is the adjacency list.
     * @param turn            is the turn object representing the player moves.
     * @param playerResources are the food and technology resources currently available.
     */
    @Override
    protected void checkMyRule(HashMap<String, List<Unit>> units,
                               HashMap<String, HashMap<String, Integer>> neighbors,
                               OnePlayerTurn turn,
                               Resources playerResources) {
        upgradeSpy(turn, units, playerResources);
        //moveSpy(turn, playerResources, neighbors);

    }

    protected void moveSpy(OnePlayerTurn turn, Resources playerResources,
                         HashMap<String, HashMap<String, Integer>> neighbors) {
        for (SpyMove move : turn.getSpyMove()) {
            String des = move.getDes();
            String src = move.getSrc();
            HashMap<String, Integer> srcNeighbors = neighbors.get(src);
            if (!srcNeighbors.containsKey(des)) {
                throw new IllegalArgumentException("Spy moves must be from adjacent territories." +
                        " Territories " + src + " and " + des + " are not adjacent.");
            }
            int dist = srcNeighbors.get(des);
            playerResources.move(dist, 1);
        }
    }

    /**
     * Conduct spy upgrades.
     * @param turn is the turn object containing all the players moves.
     * @param units is the current state of the game board.
     * @param playerResources are the current player resources available.
     */
    protected void upgradeSpy(OnePlayerTurn turn, HashMap<String, List<Unit>> units, Resources playerResources) {
        int spyCost = 15;
        for (SpyUpgrade upgrade : turn.getSpyUpgrade()) {
            String terr = upgrade.getTerrName();
            int numSpies = upgrade.getNum();
            if (!units.containsKey(terr)) {
                throw new IllegalArgumentException("Player must own the territory in which it is " +
                        "upgrading a spy. Player does not own territory " + terr + ".");
            }
            List<Unit> terrUnits = units.get(terr);
            for (Unit u : terrUnits) {
                if (u.getType() == 1) {
                    if (u.getCount() < numSpies) {
                        throw new IllegalArgumentException("You cannot upgrade " + numSpies +
                                " in territory " + terr + " because there are not that many " +
                                "level 1 units.");
                    }
                    u.setCount(u.getCount() - numSpies);
                    playerResources.consumeTechnology(numSpies*spyCost);
                }
            }
        }
    }

}
