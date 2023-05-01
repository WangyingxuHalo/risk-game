package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResGamesTest {

    @Test
    void testResGames() {
        ResGames resGames1 = new ResGames();
        assertNotNull(resGames1);

        ResGames resGames = new ResGames(new ArrayList<>());
        resGames.setGames(new ArrayList<>());
        assertTrue(resGames.getGames().isEmpty());
    }

}