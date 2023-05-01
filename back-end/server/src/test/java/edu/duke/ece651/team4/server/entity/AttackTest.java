package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackTest {

    @Test
    public void testAttack(){
        Attack attack = new Attack();
        attack.setId(1);
        attack.setUnitStrength(1);
        attack.setSrcId(1);
        attack.setDestId(1);
        attack.setOrderId(1);
        attack.setPlayerId(1);

        assertEquals(1, attack.getId());
        assertEquals(1, attack.getUnitStrength());
        assertEquals(1, attack.getSrcId());
        assertEquals(1, attack.getDestId());
        assertEquals(1, attack.getOrderId());
        assertEquals(1, attack.getPlayerId());
    }

}