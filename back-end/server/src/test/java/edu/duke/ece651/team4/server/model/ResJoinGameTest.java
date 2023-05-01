package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResJoinGameTest {

    @Test
    void testResJoinGame(){
        ResJoinGame resJoinGame1 = new ResJoinGame();
        assertNotNull(resJoinGame1);
        ResJoinGame resJoinGame = new ResJoinGame(1, "color");
        resJoinGame.setPlayerID(1);
        resJoinGame.setColor("color");

        assertEquals("color", resJoinGame.getColor());
        assertEquals(1, resJoinGame.getPlayerID());
    }

}