package edu.duke.ece651.team4.server.model;

import java.io.Serializable;

import java.util.List;

/**
 * this is the detail information of the move & acctack include in the
 * onePlaterturn
 */
public class Order implements Serializable {
    String src;
    String des;
    List<Unit> units;

    public Order() {
    }

    public Order(String src, String des, List<Unit> units) {
        this.src = src;
        this.des = des;
        this.units = units;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

}
