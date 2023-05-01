package edu.duke.ece651.team4.server.model;

import java.io.Serializable;

/*
 * this is the unit informtion included the level and num 
 */
public class Unit implements Serializable {
    int level;
    int num;

    public Unit() {
    }

    public Unit(int level, int num) {
        this.level = level;
        this.num = num;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
