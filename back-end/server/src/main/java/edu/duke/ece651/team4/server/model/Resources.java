package edu.duke.ece651.team4.server.model;

import static java.lang.Math.round;

/**
 *
 */
public class Resources {
    /**
     * Food resources available.
     */
    int foodResources;
    /**
     * Technology resources available.
     */
    int technologyResources;
    /**
     * The ratio to adjust move cost.
     */
    double moveAdjust;
    /**
     * The ratio to adjust attack cost.
     */
    double attackAdjust;

    /**
     * Public constructor for resources.
     */
    public Resources(int foodResources, int technologyResources, double moveAdjust, double attackAdjust) {
        this.foodResources = foodResources;
        this.technologyResources = technologyResources;
        this.attackAdjust = attackAdjust;
        this.moveAdjust = moveAdjust;
    }

    /**
     * Add food to resources.
     *
     * @param food is the food to add.
     */
    public void addFood(int food) {
        foodResources += food;
    }

    /**
     * Add technology to resources.
     *
     * @param tech is the technology to add.
     */
    public void addTech(int tech) {
        technologyResources += tech;
    }

    /**
     * Consume technology with an action.
     *
     * @param technology is the amount of technology to consume.
     */
    public void consumeTechnology(int technology) {
        technologyResources -= technology;
        if (technologyResources < 0) {
            throw new IllegalArgumentException("More technology consumed than available.");
        }
    }

    /**
     * Move a specific distance.
     *
     * @param distance the distance to move.
     * @param numUnits is the number of units to move.
     */
    public void move(int distance, int numUnits) {
        double cost = distance * numUnits * moveAdjust;
        foodResources -= (int) round(cost);
        if (foodResources < 0) {
            throw new IllegalArgumentException("More food consumed than available.");
        }
    }

    /**
     * Attack a specific distance.
     *
     * @param distance the distance to attack.
     * @param numUnits is the number of units to move.
     */
    public void attack(int distance, int numUnits) {
        double cost = distance * numUnits * 2 * attackAdjust;
        foodResources -= (int) round(cost);
        if (foodResources < 0) {
            throw new IllegalArgumentException("More food consumed than available.");
        }
    }

    /**
     * Getter for food resources.
     *
     * @return food resources.
     */
    public int getFoodResources() {
        return foodResources;
    }

    /**
     * getter for technology resources.
     *
     * @return technology resources.
     */
    public int getTechnologyResources() {
        return technologyResources;
    }
}
