package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

import java.util.List;

public class ResLastRoundInfo implements Serializable{
    List<ResResolve> lastRoundInfo;

    

    public ResLastRoundInfo() {
    }

    public ResLastRoundInfo(List<ResResolve> lastRoundInfo) {
        this.lastRoundInfo = lastRoundInfo;
    }

    public List<ResResolve> getLastRoundInfo() {
        return lastRoundInfo;
    }

    public void setLastRoundInfo(List<ResResolve> lastRoundInfo) {
        this.lastRoundInfo = lastRoundInfo;
    }

}
