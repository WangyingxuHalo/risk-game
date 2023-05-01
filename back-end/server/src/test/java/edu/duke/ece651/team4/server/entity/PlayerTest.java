package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    @Test
    public void testPlayer(){
        Player player = new Player();
        player.setId(1);
        player.setGameId(1);
        player.setUserId(1);
        player.setName("name");
        player.setAlive(true);
        player.setTotalFood(1);
        player.setTotalTech(1);
        player.setTechLevel(1);
        player.setPlacementDone(true);

        assertEquals(1,player.getId());
        assertEquals(1,player.getGameId());
        assertEquals(1,player.getUserId());
        assertEquals("name",player.getName());
        assertTrue(player.getAlive());
        assertEquals(1,player.getTotalFood());
        assertEquals(1,player.getTotalTech());
        assertEquals(1,player.getTechLevel());
        assertTrue(player.isPlacementDone());
    }

}