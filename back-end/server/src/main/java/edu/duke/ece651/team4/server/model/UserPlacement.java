package edu.duke.ece651.team4.server.model;

import java.io.Serializable;
import java.util.List;

/**
 * this include all the unit information the player want to place on the
 * territory which send from web to server
 */
public class UserPlacement implements Serializable {
    private int playerId;
    private List<Placement> placements;

    public UserPlacement() {
    }

    public UserPlacement(int playerId, List<Placement> placements) {
        this.playerId = playerId;
        this.placements = placements;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public List<Placement> getPlacements() {
        return placements;
    }

    public void setPlacements(List<Placement> placements) {
        this.placements = placements;
    }

}
