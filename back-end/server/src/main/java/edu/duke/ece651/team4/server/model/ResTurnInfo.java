package edu.duke.ece651.team4.server.model;

import java.io.Serializable;
import java.util.List;

public class ResTurnInfo implements Serializable {
    String playerName;
    int GameID;
    int turnNO;
    int playNum;
    int totalFood;
    int totalTech;
    int genFood;
    int genTech;

    int techLevel;

    List<String> territoryNames;

    String winnerName;

    public ResTurnInfo() {

    }

    public ResTurnInfo(String playerName, int gameID, int turnNO, int playNum, int totalFood, int totalTech,
                       int genFood, int genTech, int techLevel, List<String> territoryNames, String winnerName) {
        this.playerName = playerName;
        GameID = gameID;
        this.turnNO = turnNO;
        this.playNum = playNum;
        this.totalFood = totalFood;
        this.totalTech = totalTech;
        this.genFood = genFood;
        this.genTech = genTech;
        this.techLevel = techLevel;
        this.territoryNames = territoryNames;
        this.winnerName = winnerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public int getTurnNO() {
        return turnNO;
    }

    public void setTurnNO(int turnNO) {
        this.turnNO = turnNO;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
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

    public int getGenFood() {
        return genFood;
    }

    public void setGenFood(int genFood) {
        this.genFood = genFood;
    }

    public int getGenTech() {
        return genTech;
    }

    public void setGenTech(int genTech) {
        this.genTech = genTech;
    }

    public List<String> getTerritoryNames() {
        return territoryNames;
    }

    public void setTerritoryNames(List<String> territoryNames) {
        this.territoryNames = territoryNames;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(int techLevel) {
        this.techLevel = techLevel;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
