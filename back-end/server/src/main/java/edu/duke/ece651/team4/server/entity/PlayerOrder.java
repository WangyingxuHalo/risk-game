package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class PlayerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @PositiveOrZero
    private int turnId;

    private int playerId;

    private boolean research;

    private int gameID;

    public PlayerOrder(int turnId, int playerId, boolean research, int gameID) {
        this.turnId = turnId;
        this.playerId = playerId;
        this.research = research;
        this.gameID = gameID;
    }

    public PlayerOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTurnId() {
        return turnId;
    }

    public void setTurnId(int turnId) {
        this.turnId = turnId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean isResearch() {
        return research;
    }

    public void setResearch(boolean research) {
        this.research = research;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
