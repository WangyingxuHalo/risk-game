package edu.duke.ece651.team4.server.model;

import java.io.Serializable;

/**
 * this include the placement information for each territory which will be used
 * in UserPlacement
 */
public class Placement implements Serializable {
    private String terrName;
    private int numUnits;

    public Placement() {
    }

    public Placement(String terrName, int numUnits) {
        this.terrName = terrName;
        this.numUnits = numUnits;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public void setNumUnits(int numUnits) {
        this.numUnits = numUnits;
    }

    public String getTerrName() {
        return terrName;
    }

    public void setTerrName(String terrName) {
        this.terrName = terrName;
    }

}
