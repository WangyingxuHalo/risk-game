package edu.duke.ece651.team4.server.model;
import java.io.Serializable;
public class SpyMove implements Serializable {

    String src;
    String des;
    int num;
    public SpyMove(){}
    public SpyMove(String src, String des, int num) {
        this.src = src;
        this.des = des;
        this.num = num;
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
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }

    
}
