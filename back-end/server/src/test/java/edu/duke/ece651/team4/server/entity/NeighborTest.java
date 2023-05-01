package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NeighborTest {

    @Test
    public void testNeighbor(){
        Neighbor neighbor = new Neighbor();
        neighbor.setId(1);
        neighbor.setTerritoryId(1);
        neighbor.setNeighborId(1);
        neighbor.setDistance(1);

        assertEquals(1, neighbor.getId());
        assertEquals(1, neighbor.getTerritoryId());
        assertEquals(1, neighbor.getNeighborId());
        assertEquals(1, neighbor.getDistance());
    }

}