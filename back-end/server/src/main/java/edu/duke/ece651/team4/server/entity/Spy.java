package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Spy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @PositiveOrZero
    private int playerId;

    @PositiveOrZero
    private int territoryId;

    public Spy() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(int territoryId) {
        this.territoryId = territoryId;
    }

    public Spy(int playerId, int territoryId) {
        this.playerId = playerId;
        this.territoryId = territoryId;
    }

}
