package edu.duke.ece651.team4.server.model;
import java.io.Serializable;


public class Upgrade implements Serializable {
    int num;
    int before;
    int after;
    String terrName;


    public Upgrade() {
    }

    public Upgrade(int num, int before, int after,String terrName) {
        this.num = num;
        this.before = before;
        this.after = after;
        this.terrName = terrName;
    }

    public String getTerrName() {
        return terrName;
    }

    public void setTerrName(String terrName) {
        this.terrName = terrName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getBefore() {
        return before;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }
}
