package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResAllInfoTest {

    @Test
    void testResAllInfo(){
        ResAllInfo resAllInfo = new ResAllInfo();
        resAllInfo.setResTurnInfo(null);
        resAllInfo.setResLastRoundInfo(null);
        resAllInfo.setResGameMap(null);
        resAllInfo.setSeason(null);

        assertNull(resAllInfo.getResTurnInfo());
        assertNull(resAllInfo.getResGameMap());
        assertNull(resAllInfo.getResLastRoundInfo());
        assertNull(resAllInfo.getSeason());

    }
}