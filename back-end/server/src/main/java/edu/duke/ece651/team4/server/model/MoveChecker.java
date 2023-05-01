package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;

import java.util.*;


/**
 * Check that all moves are valid.
 */
public class MoveChecker extends ActionChecker {
    public MoveChecker(ActionChecker next) {
        super(next);
    }

    /**
     * Checks that the moves are valid. All moves are from owned territories, etc.
     *
     * @param units         is the units in each territories.
     * @param neighbors     is the adjacency list.
     * @param turn          is the turn object representing the player moves.
     * @param playerResources resources currently available.
     * @return
     */
    @Override
    protected void checkMyRule(HashMap<String, List<Unit>> units,
                              HashMap<String, HashMap<String, Integer>> neighbors,
                              OnePlayerTurn turn,
                              Resources playerResources) {
        // Check move orders.
        for (Order move : turn.getMove()) {
            String des = move.getDes();
            String src = move.getSrc();
            if (!units.containsKey(des) || !units.containsKey(src)) {
                throw new IllegalArgumentException("Player must own both territories in a move. " +
                        "Player does not own both territory " + src + " and territory " + des + ".");
            }
            int costPath = shortestPath(neighbors, src, des, neighbors.keySet());
            int numUnits = updateUnits(units, move);
            playerResources.move(costPath, numUnits);
        }
    }

    /**
     * Update the hashmap for a given move order.
     *
     * @param units     HashMap containing the units for each territory.
     * @param moveOrder the move order to try.
     * @return number of units to move.
     */
    protected int updateUnits(HashMap<String, List<Unit>> units, Order moveOrder) {
        String src = moveOrder.getSrc();
        String dst = moveOrder.getDes();
        List<Unit> srcUnits = units.get(src);
        List<Unit> dstUnits = units.get(dst);
        int totalNumUnits = 0;
        for (edu.duke.ece651.team4.server.model.Unit toMove : moveOrder.getUnits()) {
            int level = toMove.getLevel();
            for (Unit u : srcUnits) {
                if (u.getType() == level) {
                    totalNumUnits += toMove.getNum();
                    int newCount = u.getCount() - toMove.getNum();
                    if (newCount < 0) {
                        throw new IllegalArgumentException("Moves must not leave a player with " +
                                "negative units. Territory " + src + " has negative units.");
                    }
                    u.setCount(newCount);
                }
            }
            for (Unit u : dstUnits) {
                if (u.getType() == level) {
                    int newCount = u.getCount() + toMove.getNum();
                    u.setCount(newCount);
                }
            }
        }
        return totalNumUnits;
    }

    protected int shortestPath(HashMap<String, HashMap<String, Integer>> neighbors, String src,
                            String dest, Set<String> owned) {
        HashMap<String, Integer> distances = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        distances.put(src, 0);
        queue.offer(src);
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(dest)) {
                return distances.get(dest);
            }
            for (Map.Entry<String, Integer> neighbor : neighbors.get(curr).entrySet()) {
                String next = neighbor.getKey();
                if (owned.contains(next)) {
                    int weight = neighbor.getValue();
                    int dist = distances.get(curr) + weight;

                    if (!distances.containsKey(next) || dist < distances.get(next)) {
                        distances.put(next, dist);
                        queue.offer(next);
                    }
                }
            }
        }
        throw new IllegalArgumentException("No valid move path exists between " + src + " and " +
                dest + ".");
    }
}
