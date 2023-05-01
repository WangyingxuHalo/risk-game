package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeasonTest {

    @Test
    void testSeason() {
        Season season = new Season();
        season.setSeason("A");
        season.setDescription("A");


        assertEquals("A", season.getSeason());
        assertEquals("A", season.getDescription());
    }

}