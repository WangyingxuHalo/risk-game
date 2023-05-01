package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResGameMapTest {

    @Test
    void testResGameMap(){
        ResGameMap resGameMap = new ResGameMap();
        resGameMap.setMaxUnits(1);
        resGameMap.setTerritories(new HashMap<>());

        assertEquals(1, resGameMap.getMaxUnits());
        assertTrue(resGameMap.getTerritories().isEmpty());
    }

}