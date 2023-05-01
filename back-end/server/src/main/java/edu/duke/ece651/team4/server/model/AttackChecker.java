package edu.duke.ece651.team4.server.model;


import edu.duke.ece651.team4.server.entity.Unit;

import java.util.HashMap;
import java.util.List;

public class AttackChecker extends ActionChecker {

    /**
     * Public constructor.
     * @param next is the next checker in the chain, null if otherwise.
     */
    public AttackChecker(ActionChecker next) {
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
        for (Order attack : turn.getAttack()) {
            String des = attack.getDes();
            String src = attack.getSrc();
            if (units.containsKey(des) || !units.containsKey(src)) {
                throw new IllegalArgumentException("Player must own the territory they are attacking from and not own the territory the attack to. " +
                        "The attack from " + src + " to " + des + "is not valid.");
            }
            if (!neighbors.get(src).containsKey(des)) {
                throw new IllegalArgumentException("The territories involved in an attack must be" +
                        " adjacent. " + src + " and " + des + " are not adjacent.");
            }
            int dist = neighbors.get(src).get(des);
            int attackUnits = checkNumUnits(units, attack);
            playerResources.attack(dist, attackUnits);
        }
    }

    /**
     * Check that the number of units attacking from a territory (and level) are valid.
     * @param unitsByTerritory is the list of units in each territory before the attacks.
     * @param attack is the specific attack order to check.
     * @return an integer representing the total number of units to attack.
     */
    protected int checkNumUnits(HashMap<String, List<Unit>> unitsByTerritory, Order attack) {
        String src = attack.getSrc();
        int totalNumUnits = 0;
        List<Unit> srcUnits = unitsByTerritory.get(src);
        for (edu.duke.ece651.team4.server.model.Unit toAttack : attack.getUnits()) {
            int level = toAttack.getLevel();
            for (Unit u : srcUnits) {
                if (u.getType() == level) {
                    totalNumUnits += toAttack.getNum();
                    int newCount = u.getCount() - toAttack.getNum();
                    if (newCount < 0) {
                        throw new IllegalArgumentException("Attacks must not leave a player with " +
                                "negative units. Territory " + src + " has negative units.");
                    }
                    u.setCount(newCount);
                }
            }
        }
        return totalNumUnits;
    }
}
