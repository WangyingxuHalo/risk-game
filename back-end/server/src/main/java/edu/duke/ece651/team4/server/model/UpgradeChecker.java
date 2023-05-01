package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;

import java.util.HashMap;
import java.util.List;

/**
 * Check that all moves are valid.
 */
public class UpgradeChecker extends ActionChecker {
    public UpgradeChecker(ActionChecker next) {
        super(next);
    }

    /**
     * Checks that the player have enough food to upgrade.
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

        int[] cost = { 0, 3, 8, 19, 25, 35, 50 };

        for (Upgrade p : turn.getUpgrade()) {
            // after is lager than before;

            if (p.getBefore() < 0 || p.getAfter() < 0) {
                throw new IllegalArgumentException("Units level should always be positive.");
            }
            if (p.getBefore() >= p.getAfter()) {
                throw new IllegalArgumentException("Units can only be upgraded.");
            }
            // after is less than techlevel;
            if (p.getAfter() > turn.getTechLevel()) {
                throw new IllegalArgumentException(
                        "You does not have enough technology level to upgrade the units, please do research first.");
            }
            // vaild territories food

            if (!units.containsKey(p.getTerrName())) {
                throw new IllegalArgumentException("Player must own the territories to upgrade the unit");
            }

            // vaild enough food
            int costPerUnit = 0;
            for (int i = p.getBefore(); i < p.getAfter(); i++) {
                costPerUnit += p.getNum() * cost[i + 1];
            }
            playerResources.consumeTechnology(costPerUnit);
            // change the number
            updateUnits(units,p);
            }
        }

    /**
     * Update the hashmap for a given upgrade.
     *
     * @param units     HashMap containing the units for each territory.
     * @param p the Upgrade order to try.
     * 
     */
    protected void updateUnits(HashMap<String, List<Unit>> units, Upgrade p) {
        List<Unit> unit = units.get(p.getTerrName());
        boolean type_flag=false;
        for (Unit u : unit) {
            if (u.getType() == p.getBefore()) {
                type_flag = true;
                // remove old level units

                if (u.getCount() < p.getNum()) {
                    throw new IllegalArgumentException(
                            "Player does not have enough units at level " + p.getBefore() + " to upgrade.");
                } else {
                    u.setCount(u.getCount() - p.getNum());
                }
            }
            // add new level units
            if (u.getType() == p.getAfter()) {
                    u.setCount(u.getCount() + p.getNum());
            }
    }
        if(!type_flag){
            throw new IllegalArgumentException(
                    "Player does not have any level"+ p.getBefore() + " unit.");
        }
    }
}
