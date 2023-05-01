package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResMapTerrTest {

    @Test
    void testResMapTerr() {
        ResMapTerr resMapTerr1 = new ResMapTerr();
        assertNotNull(resMapTerr1);
        ResMapTerr resMapTerr = new ResMapTerr("color", new ArrayList<>(),0,"A");
        resMapTerr.setColor("color");
        resMapTerr.setNumUnit(new ArrayList<>());
        assertEquals("color", resMapTerr.getColor());
        assertTrue(resMapTerr.getNumUnit().size() == 0);
        assertEquals("A", resMapTerr.getOwner());
        assertEquals(0, resMapTerr.getSpyNum());

    }

}