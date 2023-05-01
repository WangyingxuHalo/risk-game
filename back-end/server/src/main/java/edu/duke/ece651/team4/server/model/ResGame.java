package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

public class ResGame implements Serializable {
    int gameID;
    int playerID;
    String color;

    
    public ResGame() {
    }
    public ResGame(int gameID, int playerID, String color) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.color = color;
    }
    public int getGameID() {
        return gameID;
    }
    public void setGameID(int gameID) {
        this.gameID = gameID;
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
