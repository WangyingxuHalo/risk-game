package edu.duke.ece651.team4.server.model;

import java.io.Serializable;
import java.util.List;

/**
 * this is the user's choice information in the turn the web send to server
 */

public class OnePlayerTurn implements Serializable {
    int playerID;
    int turnNum;
    List<Order> move;
    List<Order> attack;
    Boolean research;
    int techLevel;
    List<Upgrade> upgrade;
    List<SpyMove> spyMove;
    List<SpyUpgrade> spyUpgrade;
    List<String> clocking;


    public OnePlayerTurn() {
    }

    public OnePlayerTurn(int playerID, int turnNum, List<Order> move, List<Order> attack,Boolean research,int techLevel,List<Upgrade> upgrade,
    List<SpyMove> spyMove,
    List<SpyUpgrade> spyUpgrade,
    List<String> clocking) {
        this.playerID = playerID;
        this.turnNum = turnNum;
        this.move = move;
        this.attack = attack;
        this.research = research;
        this.techLevel = techLevel;
        this.upgrade = upgrade;
        this.spyMove =spyMove;
        this.spyUpgrade = spyUpgrade;
        this.clocking = clocking;
    }

    public List<SpyMove> getSpyMove() {
        return spyMove;
    }

    public void setSpyMove(List<SpyMove> spyMove) {
        this.spyMove = spyMove;
    }

    public List<SpyUpgrade> getSpyUpgrade() {
        return spyUpgrade;
    }

    public void setSpyUpgrade(List<SpyUpgrade> spyUpgrade) {
        this.spyUpgrade = spyUpgrade;
    }

    public List<String> getClocking() {
        return clocking;
    }

    public void setClocking(List<String> clocking) {
        this.clocking = clocking;
    }

    public Boolean getResearch() {
        return research;
    }

    public void setResearch(Boolean research) {
        this.research = research;
    }

    public List<Upgrade> getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(List<Upgrade> upgrade) {
        this.upgrade = upgrade;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public void setTurnNum(int turnNum) {
        this.turnNum = turnNum;
    }

    public List<Order> getMove() {
        return move;
    }

    public void setMove(List<Order> move) {
        this.move = move;
    }

    public List<Order> getAttack() {
        return attack;
    }

    public void setAttack(List<Order> attack) {
        this.attack = attack;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(int techLevel) {
        this.techLevel = techLevel;
    }

}
