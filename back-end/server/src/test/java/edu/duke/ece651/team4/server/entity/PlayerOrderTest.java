package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerOrderTest {

    @Test
    public void testPlayerOrder(){
        PlayerOrder playerOrder = new PlayerOrder();
        playerOrder.setId(1);
        playerOrder.setTurnId(1);
        playerOrder.setPlayerId(1);
        playerOrder.setResearch(true);
        playerOrder.setGameID(1);

        assertEquals(1,playerOrder.getId());
        assertEquals(1,playerOrder.getTurnId());
        assertEquals(1,playerOrder.getPlayerId());
        assertTrue(playerOrder.isResearch());
        assertEquals(1,playerOrder.getGameID());
    }

}