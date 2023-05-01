package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitViewTest {
    @Test
    public void testUnitView() {
        UnitView uView = new UnitView(1, 15, 23);
        uView.setId(2);
        assertEquals(2, uView.getId());
        assertEquals(uView.getTerritoryViewId(), 23);
        assertEquals(uView.getType(), 1);
        assertEquals(uView.getCount(), 15);
        uView.setType(2);
        uView.setCount(16);
        uView.setTerritoryViewId(24);
        assertEquals(uView.getTerritoryViewId(), 24);
        assertEquals(uView.getType(), 2);
        assertEquals(uView.getCount(), 16);
    }

}