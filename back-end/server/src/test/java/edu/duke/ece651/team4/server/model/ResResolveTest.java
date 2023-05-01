package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResResolveTest {

    @Test
    void testResResolve(){
        ResResolve resResolve = new ResResolve();
        resResolve.setAttackedIt(new ArrayList<>());
        resResolve.setOwner("owner");
        resResolve.setWinner("winner");
        resResolve.setAttackedTerritory("attack");

        assertEquals("owner", resResolve.getOwner());
        assertEquals("winner", resResolve.getWinner());
        assertEquals("attack", resResolve.getAttackedTerritory());
        assertTrue(resResolve.getAttackedIt().isEmpty());
    }

}