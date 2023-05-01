package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Neighbor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int territoryId;

    private int neighborId;

    @PositiveOrZero
    private int distance;

    public Neighbor() {
    }

    public Neighbor(int territoryId, int neighborId, int distance) {
        this.territoryId = territoryId;
        this.neighborId = neighborId;
        this.distance = distance;
    }

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

    public int getNeighborId() {
        return neighborId;
    }

    public void setNeighborId(int neighborId) {
        this.neighborId = neighborId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
