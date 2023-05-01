package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TerritoryViewTest {
    @Test
    public void testTerritoryView() {
        TerritoryView tView = new TerritoryView(45, 1, 3);
        tView.setId(1);
        assertEquals(tView.getId(), 1);
        assertEquals(tView.getTerritoryId(), 45);
        assertEquals(tView.getOwnerId(), 1);
        assertEquals(tView.getPlayerId(), 3);
        tView.setTerritoryId(46);
        tView.setOwnerId(2);
        tView.setPlayerId(4);
        assertEquals(tView.getTerritoryId(), 46);
        assertEquals(tView.getOwnerId(), 2);
        assertEquals(tView.getPlayerId(), 4);
    }



}