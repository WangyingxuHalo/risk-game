package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Territory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int ownerId;

    private int gameId;

    @NotBlank
    private String name;

    @PositiveOrZero
    private int foodGeneration;

    public int getCloak() {
        return cloak;
    }

    public void setCloak(int cloak) {
        this.cloak = cloak;
    }

    @PositiveOrZero
    private int techGeneration;

    @PositiveOrZero
    private int cloak;

    public Territory() {
    }

    public Territory(int ownerId, int gameId, String name,
                     int foodGeneration, int techGeneration) {
        this.ownerId = ownerId;
        this.gameId = gameId;
        this.name = name;
        this.foodGeneration = foodGeneration;
        this.techGeneration = techGeneration;
        this.cloak = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodGeneration() {
        return foodGeneration;
    }

    public void setFoodGeneration(int foodGeneration) {
        this.foodGeneration = foodGeneration;
    }

    public int getTechGeneration() {
        return techGeneration;
    }

    public void setTechGeneration(int techGeneration) {
        this.techGeneration = techGeneration;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
