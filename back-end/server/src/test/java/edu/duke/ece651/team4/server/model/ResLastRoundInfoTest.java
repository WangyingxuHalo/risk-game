package edu.duke.ece651.team4.server.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResLastRoundInfoTest {

    @Test
    void testResLastRoundInfo(){
        ResLastRoundInfo resLastRoundInfo = new ResLastRoundInfo();
        resLastRoundInfo.setLastRoundInfo(new ArrayList<>());
        assertTrue(resLastRoundInfo.getLastRoundInfo().isEmpty());
    }
}