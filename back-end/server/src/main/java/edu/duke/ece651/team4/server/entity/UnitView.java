package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class UnitView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int type;

    @PositiveOrZero
    private int count;

    private int territoryViewId;

    public UnitView() {
    }

    public UnitView(int type, int count, int territoryViewId) {
        this.type = type;
        this.count = count;
        this.territoryViewId = territoryViewId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTerritoryViewId() {
        return territoryViewId;
    }

    public void setTerritoryViewId(int territoryViewId) {
        this.territoryViewId = territoryViewId;
    }
}
