package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResTurnInfoTest {

    @Test
    void testResTurnInfo(){
        ResTurnInfo resTurnInfo = new ResTurnInfo();
        resTurnInfo.setPlayerName("name");
        resTurnInfo.setGameID(1);
        resTurnInfo.setTurnNO(1);
        resTurnInfo.setPlayNum(1);
        resTurnInfo.setTotalFood(1);
        resTurnInfo.setGenFood(1);
        resTurnInfo.setTechLevel(1);
        resTurnInfo.setGenTech(1);
        resTurnInfo.setTotalTech(1);
        resTurnInfo.setTerritoryNames(new ArrayList<>());
        resTurnInfo.setWinnerName("winner");

        assertEquals(1, resTurnInfo.getGameID());
        assertEquals(1, resTurnInfo.getTurnNO());
        assertEquals(1, resTurnInfo.getPlayNum());
        assertEquals(1, resTurnInfo.getTotalFood());
        assertEquals(1, resTurnInfo.getGenFood());
        assertEquals(1, resTurnInfo.getTechLevel());
        assertEquals(1, resTurnInfo.getGenTech());
        assertEquals(1, resTurnInfo.getTotalTech());
        assertEquals("name", resTurnInfo.getPlayerName());
        assertEquals("winner", resTurnInfo.getWinnerName());
        assertTrue(resTurnInfo.getTerritoryNames().isEmpty());
    }

}