package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

import java.util.List;

public class ResGames implements Serializable{
    List<ResGame> games;

    public ResGames() {
    }

    public ResGames(List<ResGame> games) {
        this.games = games;
    }

    public List<ResGame> getGames() {
        return games;
    }

    public void setGames(List<ResGame> games) {
        this.games = games;
    }
    
}
