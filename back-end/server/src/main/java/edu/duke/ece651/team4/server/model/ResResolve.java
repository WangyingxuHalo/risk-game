package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

import java.util.List;

public class ResResolve implements Serializable {
    String attackedTerritory;
    String owner;
    String winner;
    List<ResAttacker> attackedIt;


    
    public ResResolve() {
    }
    
    public ResResolve(String attackedTerritory, String owner, String winner, List<ResAttacker> attackedIt) {
        this.attackedTerritory = attackedTerritory;
        this.owner = owner;
        this.winner = winner;
        this.attackedIt = attackedIt;
    }
    public String getAttackedTerritory() {
        return attackedTerritory;
    }
    public void setAttackedTerritory(String attackedTerritory) {
        this.attackedTerritory = attackedTerritory;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }
    public List<ResAttacker> getAttackedIt() {
        return attackedIt;
    }
    public void setAttackedIt(List<ResAttacker> attackedIt) {
        this.attackedIt = attackedIt;
    }

    

}
