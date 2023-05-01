package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResAttackerTest {

    @Test
    void testResAttacker(){
        ResAttacker resAttacker = new ResAttacker();
        resAttacker.setName("name");
        resAttacker.setAttackTerr(new ArrayList<>());

        assertEquals("name", resAttacker.getName());
        assertTrue(resAttacker.getAttackTerr().isEmpty());
    }

}