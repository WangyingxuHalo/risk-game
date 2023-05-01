package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

import java.util.List;

public class ResAttacker implements Serializable {
    String name;
    List<String>attackTerr;

    
    public ResAttacker() {
    }

    
    public ResAttacker(String name, List<String> attackTerr) {
        this.name = name;
        this.attackTerr = attackTerr;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getAttackTerr() {
        return attackTerr;
    }
    public void setAttackTerr(List<String> attackTerr) {
        this.attackTerr = attackTerr;
    }

}
