package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpyTest {

    @Test
    void testSpyMove() {
        SpyMove spymove = new  SpyMove();
        spymove.setNum(1);
        spymove.setDes("des");
        spymove.setSrc("src");
        assertEquals(1, spymove.getNum());
        assertEquals("des", spymove.getDes());
        assertEquals("src", spymove.getSrc());
    }


    @Test
    void testSpyUpgrade() {
        SpyUpgrade spyupgrade = new  SpyUpgrade();
        spyupgrade.setNum(1);
        spyupgrade.setTerrName("ter");
        assertEquals(1, spyupgrade.getNum());
        assertEquals("ter", spyupgrade.getTerrName());
    }
}