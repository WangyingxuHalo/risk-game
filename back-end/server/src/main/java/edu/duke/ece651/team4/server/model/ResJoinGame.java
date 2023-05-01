package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

public class ResJoinGame implements Serializable{
    int playerID;

    String color;
    
    public ResJoinGame() {
    }

    public ResJoinGame(int playerID, String color) {
        this.playerID = playerID;
        this.color = color;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
