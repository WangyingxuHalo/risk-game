package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ResolveDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int attackerId;

    private int territoryId;

    private int resolveId;

    public ResolveDetail() {
    }

    public ResolveDetail(int attackerId, int territoryId, int resolveId) {
        this.attackerId = attackerId;
        this.territoryId = territoryId;
        this.resolveId = resolveId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttackerId() {
        return attackerId;
    }

    public void setAttackerId(int attackerId) {
        this.attackerId = attackerId;
    }

    public int getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(int territoryId) {
        this.territoryId = territoryId;
    }

    public int getResolveId() {
        return resolveId;
    }

    public void setResolveId(int resolveId) {
        this.resolveId = resolveId;
    }
}
