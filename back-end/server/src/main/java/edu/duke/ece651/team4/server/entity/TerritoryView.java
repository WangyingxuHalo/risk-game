package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class TerritoryView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @PositiveOrZero
    private int territoryId;

    @PositiveOrZero
    private int ownerId;

    @PositiveOrZero
    private int playerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(int territoryId) {
        this.territoryId = territoryId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public TerritoryView() {
    }

    public TerritoryView(int territoryId, int ownerId, int playerId) {
        this.territoryId = territoryId;
        this.ownerId = ownerId;
        this.playerId = playerId;
    }
}
