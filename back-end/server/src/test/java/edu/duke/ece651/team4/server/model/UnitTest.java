package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Test
    void testUnit(){
        Unit unit = new Unit();
        unit.setLevel(1);
        unit.setNum(1);

        assertEquals(1, unit.getLevel());
        assertEquals(1, unit.getNum());
    }
}