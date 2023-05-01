package edu.duke.ece651.team4.server.model;
import java.io.Serializable;
public class SpyUpgrade implements Serializable {
    
    int num;
    String terrName;

    public SpyUpgrade(){
        
    }
    public SpyUpgrade(int num, String terrName) {
        this.num = num;
        this.terrName = terrName;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getTerrName() {
        return terrName;
    }
    public void setTerrName(String terrName) {
        this.terrName = terrName;
    }
    
}
