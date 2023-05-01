package edu.duke.ece651.team4.server.model;
import java.io.Serializable;


public class Season implements Serializable {
    String season;
    String description;
    
    public Season() {
    }
    
    public Season(String season, String description) {
        this.season = season;
        this.description = description;
    }

    public String getSeason() {
        return season;
    }
    public void setSeason(String season) {
        this.season = season;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
