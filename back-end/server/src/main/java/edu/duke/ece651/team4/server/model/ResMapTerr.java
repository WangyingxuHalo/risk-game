package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

import java.util.List;

public class ResMapTerr implements Serializable{
    String color;
    List<Unit> numUnit;
    int spyNum;
    String owner;
public ResMapTerr() {
    }
    
    public ResMapTerr(String color, List<Unit> numUnit,int spyNum, String owner ) {
        this.color = color;
        this.numUnit = numUnit;
        this.spyNum = spyNum;
        this. owner = owner;
    }

   
 public int getSpyNum() {
        return spyNum;
    }

    public void setSpyNum(int spyNum) {
        this.spyNum = spyNum;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public List<Unit> getNumUnit() {
        return numUnit;
    }
    public void setNumUnit(List<Unit> numUnit) {
        this.numUnit = numUnit;
    }

    
}
