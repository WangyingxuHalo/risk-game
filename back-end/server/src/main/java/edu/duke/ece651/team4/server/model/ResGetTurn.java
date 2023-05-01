package edu.duke.ece651.team4.server.model;
import java.io.Serializable;

public class ResGetTurn implements Serializable {
    int turn;

    public ResGetTurn() {
    }

    public ResGetTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    
}
