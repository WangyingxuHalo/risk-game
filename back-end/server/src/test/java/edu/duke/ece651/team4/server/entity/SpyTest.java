package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpyTest {

    @Test
    void getAndSetId() {
        Spy spy = new Spy(4, 14);
        int id = spy.getId();
        assertEquals(id, spy.getId());
        spy.setId(123);
        assertEquals(123, spy.getId());
    }

    @Test
    void getAndSetPlayerId() {
        Spy spy = new Spy(4, 14);
        assertEquals(spy.getPlayerId(), 4);
        spy.setPlayerId(1);
        assertEquals(1, spy.getPlayerId());
    }

    @Test
    void getAndSetTerritoryId() {
        Spy spy = new Spy(4, 14);
        assertEquals(spy.getTerritoryId(), 14);
        spy.setTerritoryId(2309);
        assertEquals(2309, spy.getTerritoryId());
    }
}