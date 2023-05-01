package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

import java.util.Map;

public class ResGameMap implements Serializable{
    Map<String,ResMapTerr> territories;

    int maxUnits;
    
    public ResGameMap() {
    }

    public ResGameMap(Map<String, ResMapTerr> territories, int maxUnits) {
        this.territories = territories;
        this.maxUnits = maxUnits;
    }

    public Map<String, ResMapTerr> getTerritories() {
        return territories;
    }

    public void setTerritories(Map<String, ResMapTerr> territories) {
        this.territories = territories;
    }

    public int getMaxUnits() {
        return maxUnits;
    }

    public void setMaxUnits(int maxUnits) {
        this.maxUnits = maxUnits;
    }
}
