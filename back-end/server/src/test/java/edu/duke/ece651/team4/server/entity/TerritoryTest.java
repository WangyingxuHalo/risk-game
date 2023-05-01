package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TerritoryTest {

    @Test
    public void testTerritory(){
        Territory territory = new Territory();
        territory.setId(1);
        territory.setOwnerId(1);
        territory.setGameId(1);
        territory.setName("name");
        territory.setFoodGeneration(1);
        territory.setTechGeneration(1);
        territory.setCloak(3);

        assertEquals(1,territory.getId());
        assertEquals(1,territory.getOwnerId());
        assertEquals(1,territory.getGameId());
        assertEquals("name",territory.getName());
        assertEquals(1,territory.getFoodGeneration());
        assertEquals(1,territory.getTechGeneration());
        assertEquals(3, territory.getCloak());
    }

}