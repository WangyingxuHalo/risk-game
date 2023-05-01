package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResolveTest {

    @Test
    public void testResolve(){
        Resolve resolve = new Resolve();
        resolve.setId(1);
        resolve.setWinnerId(1);
        resolve.setOriginalOwnerId(1);
        resolve.setAttackedTerritoryId(1);
        resolve.setGameId(1);

        assertEquals(1,resolve.getId());
        assertEquals(1,resolve.getWinnerId());
        assertEquals(1,resolve.getOriginalOwnerId());
        assertEquals(1,resolve.getAttackedTerritoryId());
        assertEquals(1,resolve.getGameId());
    }

}