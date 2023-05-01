package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @PositiveOrZero
    private int numPlayers;

    private int numAlivePlayers;

    @NotNull
    private Boolean isGameOver;

    @PositiveOrZero
    private int turnNum;

    public Game() {
    }

    public Game(int numPlayers) {
        this.numAlivePlayers = numPlayers;
        this.numPlayers = numPlayers;
        this.isGameOver = false;
        this.turnNum = 1;
    }

    public int getNumAlivePlayers() {
        return numAlivePlayers;
    }

    public void setNumAlivePlayers(int numAlivePlayers) {
        this.numAlivePlayers = numAlivePlayers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getTurnNum() {return turnNum;}

    public void setTurnNum(int turnNum) {this.turnNum = turnNum;}

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Boolean getGameOver() {
        return isGameOver;
    }

    public void setGameOver(Boolean gameOver) {
        isGameOver = gameOver;
    }
}
