package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @PositiveOrZero
    private int gameId;
    @NotNull
    private String season;
    @NotNull
    private String message;
    @PositiveOrZero
    private double techAdjust;
    @PositiveOrZero
    private double foodAdjust;
    @PositiveOrZero
    private double attackAdjust;
    @PositiveOrZero
    private double moveAdjust;

    public Season() {
    }

    public Season(int gameId, String season, String message, double techAdjust,
                  double foodAdjust, double attackAdjust, double moveAdjust) {
        this.gameId = gameId;
        this.season = season;
        this.message = message;
        this.techAdjust = techAdjust;
        this.foodAdjust = foodAdjust;
        this.attackAdjust = attackAdjust;
        this.moveAdjust = moveAdjust;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getTechAdjust() {
        return techAdjust;
    }

    public void setTechAdjust(double techAdjust) {
        this.techAdjust = techAdjust;
    }

    public double getFoodAdjust() {
        return foodAdjust;
    }

    public void setFoodAdjust(double foodAdjust) {
        this.foodAdjust = foodAdjust;
    }

    public double getAttackAdjust() {
        return attackAdjust;
    }

    public void setAttackAdjust(double attackAdjust) {
        this.attackAdjust = attackAdjust;
    }

    public double getMoveAdjust() {
        return moveAdjust;
    }

    public void setMoveAdjust(double moveAdjust) {
        this.moveAdjust = moveAdjust;
    }
}
