package edu.duke.ece651.team4.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private int gameId;

    private Integer userId;

    @NotBlank
    private String name;

    @NotNull
    private Boolean isAlive;

    private int totalFood;

    private int totalTech;

    private int techLevel;

    private boolean isPlacementDone;

    public Player() {
    }

    public Player(int gameId, Integer userId, String name) {
        this.gameId = gameId;
        this.userId = userId;
        this.name = name;
        this.isAlive = true;
        this.totalFood = 40;
        this.totalTech = 40;
        this.techLevel = 1;
        isPlacementDone = false;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(int techLevel) {
        this.techLevel = techLevel;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public void setTotalFood(int totalFood) {
        this.totalFood = totalFood;
    }

    public int getTotalTech() {
        return totalTech;
    }

    public void setTotalTech(int totalTech) {
        this.totalTech = totalTech;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isPlacementDone() {
        return isPlacementDone;
    }

    public void setPlacementDone(boolean placementDone) {
        isPlacementDone = placementDone;
    }
}
