package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    @Test
    public void testGame(){
        Game game = new Game();
        game.setId(1);
        game.setNumPlayers(1);
        game.setNumAlivePlayers(1);
        game.setGameOver(true);
        game.setTurnNum(1);

        assertEquals(1, game.getId());
        assertEquals(1, game.getNumPlayers());
        assertEquals(1, game.getNumAlivePlayers());
        assertTrue(game.getGameOver());
        assertEquals(1, game.getTurnNum());
    }

}