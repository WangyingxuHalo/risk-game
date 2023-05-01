package edu.duke.ece651.team4.server.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResolveDetailTest {

    @Test
    public void testResolveDetail(){
        ResolveDetail resolveDetail = new ResolveDetail();
        resolveDetail.setId(1);
        resolveDetail.setAttackerId(1);
        resolveDetail.setTerritoryId(1);
        resolveDetail.setResolveId(1);

        assertEquals(1,resolveDetail.getId());
        assertEquals(1,resolveDetail.getAttackerId());
        assertEquals(1,resolveDetail.getTerritoryId());
        assertEquals(1,resolveDetail.getResolveId());
    }

}