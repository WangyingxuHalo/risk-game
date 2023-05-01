package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitTest {

    @Test
    public void testUnit(){
        Unit territory = new Unit();
        territory.setId(1);
        territory.setType(1);
        territory.setCount(1);
        territory.setTerritoryId(1);

        assertEquals(1,territory.getId());
        assertEquals(1,territory.getType());
        assertEquals(1,territory.getCount());
        assertEquals(1,territory.getTerritoryId());
    }

}