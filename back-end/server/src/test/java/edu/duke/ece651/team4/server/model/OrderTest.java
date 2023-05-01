package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrder(){
        Order order = new Order();
        order.setDes("des");
        order.setSrc("src");
        order.setUnits(new ArrayList<>());

        assertEquals("src", order.getSrc());
        assertEquals("des", order.getDes());
        assertTrue(order.getUnits().isEmpty());
    }

}