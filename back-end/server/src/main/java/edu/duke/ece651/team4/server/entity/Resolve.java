package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Resolve {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int winnerId;

    private int originalOwnerId;

    private int attackedTerritoryId;

    private int gameId;

    public Resolve() {
    }

    public Resolve(int winnerId, int originalOwnerId, int attackedTerritoryId, int gameId) {
        this.winnerId = winnerId;
        this.originalOwnerId = originalOwnerId;
        this.attackedTerritoryId = attackedTerritoryId;
        this.gameId = gameId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public int getOriginalOwnerId() {
        return originalOwnerId;
    }

    public void setOriginalOwnerId(int originalOwnerId) {
        this.originalOwnerId = originalOwnerId;
    }

    public int getAttackedTerritoryId() {
        return attackedTerritoryId;
    }

    public void setAttackedTerritoryId(int attackedTerritoryId) {
        this.attackedTerritoryId = attackedTerritoryId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
