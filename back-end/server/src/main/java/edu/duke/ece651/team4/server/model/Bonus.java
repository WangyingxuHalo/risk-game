package edu.duke.ece651.team4.server.model;

import java.io.Serializable;

public class Bonus implements Serializable {

    String type;
    String terr;
    int playerId;

    public Bonus() {
    }

    public Bonus(String type, String terr, int playerId) {
        this.type = type;
        this.terr = terr;
        this.playerId = playerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTerr() {
        return terr;
    }

    public void setTerr(String terr) {
        this.terr = terr;
    }


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
