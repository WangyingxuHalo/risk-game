package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeTest {

    @Test
    void testUpgrade() {
        Upgrade upgrade = new Upgrade();
        upgrade.setAfter(1);
        upgrade.setNum(1);
        upgrade.setBefore(1);
        upgrade.setTerrName("name");

        assertEquals("name", upgrade.getTerrName());
        assertEquals(1, upgrade.getAfter());
        assertEquals(1, upgrade.getBefore());
        assertEquals(1, upgrade.getNum());

    }
}