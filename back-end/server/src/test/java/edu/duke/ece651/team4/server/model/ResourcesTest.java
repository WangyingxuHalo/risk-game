package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesTest {

    @Test
    void testResources() {
        Resources resources = new Resources(1, 1, 1.0, 1.0);
        resources.addFood(1);
        resources.addTech(1);
        assertEquals(2, resources.getFoodResources());
        assertEquals(2, resources.getTechnologyResources());
        resources.move(1, 1);
        resources.consumeTechnology(1);
        assertEquals(1, resources.getFoodResources());
        assertEquals(1, resources.getTechnologyResources());
        try {
            resources.move(2, 1);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            resources.consumeTechnology(3);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void test_attack_and_move() {
        Resources resources = new Resources(40, 40, 2, 0.5);
        resources.move(4, 3);
        assertEquals(resources.getFoodResources(), 16);
        resources.addFood(34);
        resources.attack(10, 2);
        assertEquals(resources.getFoodResources(), 30);
        assertThrows(IllegalArgumentException.class, () -> resources.attack(16, 2));
    }


}