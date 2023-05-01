package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeasonTest {

    @Test
    void testSeason() {
        Season s = new Season(4, "Fall", "Test Message",
                1.0, 1.5, 2.0, 2.5);
        s.setId(1);
        s.setGameId(4);

        assertEquals(1, s.getId());
        assertEquals("Fall", s.getSeason());
        assertEquals("Test Message", s.getMessage());
        assertEquals(1.0, s.getTechAdjust(), .00001);
        assertEquals(1.5, s.getFoodAdjust(), .00001);
        assertEquals(2.0, s.getAttackAdjust(), .00001);
        assertEquals(2.5, s.getMoveAdjust(), .00001);
        s.setSeason("Winter");
        s.setMessage("Test message");
        s.setTechAdjust(1.5);
        s.setFoodAdjust(2.0);
        s.setAttackAdjust(2.5);
        s.setMoveAdjust(3.0);
        assertEquals("Winter", s.getSeason());
        assertEquals("Test message", s.getMessage());
        assertEquals(1.5, s.getTechAdjust(), .00001);
        assertEquals(2.0, s.getFoodAdjust(), .00001);
        assertEquals(2.5, s.getAttackAdjust(), .00001);
        assertEquals(3.0, s.getMoveAdjust(), .00001);
    }
}