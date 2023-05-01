package edu.duke.ece651.team4.server.model;

import java.io.Serializable;

public class ResAllInfo implements Serializable {
    ResGameMap resGameMap;
    ResTurnInfo resTurnInfo;
    ResLastRoundInfo resLastRoundInfo;
    Season season;

    
    public ResAllInfo() {
    }


    public ResAllInfo(ResGameMap resGameMap, ResTurnInfo resTurnInfo, ResLastRoundInfo resLastRoundInfo,Season season) {
        this.resGameMap = resGameMap;
        this.resTurnInfo = resTurnInfo;
        this.resLastRoundInfo = resLastRoundInfo;
        this.season= season;
    }


    public ResGameMap getResGameMap() {
        return resGameMap;
    }


    public void setResGameMap(ResGameMap resGameMap) {
        this.resGameMap = resGameMap;
    }


    public ResTurnInfo getResTurnInfo() {
        return resTurnInfo;
    }


    public void setResTurnInfo(ResTurnInfo resTurnInfo) {
        this.resTurnInfo = resTurnInfo;
    }

    public ResLastRoundInfo getResLastRoundInfo() {
        return resLastRoundInfo;
    }

    public void setResLastRoundInfo(ResLastRoundInfo resLastRoundInfo) {
        this.resLastRoundInfo = resLastRoundInfo;
    }


    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
