package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementTest {

    @Test
    void testPlacement(){
        Placement placement = new Placement();
        placement.setNumUnits(1);
        placement.setTerrName("name");

        assertEquals(1, placement.getNumUnits());
        assertEquals("name", placement.getTerrName());
    }

}