package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.service.GameService;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResearchCheckerTest {

//    @Autowired
//    GameService gameService;


    @Test
    void check_my_rule() {
        ResearchChecker researchChecker = new ResearchChecker(null);
        //You already research the highest technology level.
        OnePlayerTurn turn1 = new OnePlayerTurn(0, 0, null, null,
                true, 6, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        OnePlayerTurn turn2 = new OnePlayerTurn(0, 0, null, null,
                true, 7, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        Resources playerResources = new Resources(20, 20, 1.0, 1.0);

        IllegalArgumentException thrown1 = assertThrows(IllegalArgumentException.class,
                () -> researchChecker.checkTurn(null, null, turn1, playerResources));

        assertEquals("You already research the highest technology level.", thrown1.getMessage());


        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class,
                () -> researchChecker.checkTurn(null, null, turn2, playerResources));

        assertEquals("You already research the highest technology level.", thrown2.getMessage());

        OnePlayerTurn turn3 = new OnePlayerTurn(0, 0, null, null,
                true, 4, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class,
                () -> researchChecker.checkTurn(null, null, turn3, playerResources));

        assertEquals("More technology consumed than available.", thrown3.getMessage());

        Resources playerResources2 = new Resources(20, 200, 1.0, 1.0);
        researchChecker.checkTurn(null, null, turn3, playerResources2);

        OnePlayerTurn turn4 = new OnePlayerTurn(0, 0, null, null,
                false, 7, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        researchChecker.checkTurn(null, null, turn4, playerResources2);

    }

}