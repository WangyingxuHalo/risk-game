package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResGameTest {

    @Test
    void testResGame(){
        ResGame resGame = new ResGame();
        resGame.setGameID(1);
        resGame.setPlayerID(1);
        resGame.setColor("color");

        assertEquals(1, resGame.getGameID());
        assertEquals(1, resGame.getPlayerID());
        assertEquals("color", resGame.getColor());
    }
}