package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResGetTurnTest {

    @Test
    void testResGetTurn(){
        ResGetTurn resGetTurn = new ResGetTurn();
        resGetTurn.setTurn(1);

        assertEquals(1, resGetTurn.getTurn());
    }
}