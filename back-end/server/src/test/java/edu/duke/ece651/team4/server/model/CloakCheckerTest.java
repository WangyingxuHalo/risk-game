package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.service.GameService;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CloakCheckerTest {

//    @Autowired
//    GameService gameService;
    @Test
    void check_my_rule() {
        CloakChecker cloakChecker = new CloakChecker(null);
        Resources playerResources = new Resources(20, 25, 1, 1);

        //tech level need to at least 3
        List<String> cloak = new ArrayList<>();
        cloak.add("duke");
        OnePlayerTurn turn = new OnePlayerTurn(0, 0, null, null,
                false, 2,new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),cloak);


        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> cloakChecker.checkTurn(new HashMap<>(), null, turn, playerResources));

        assertEquals("You do not have enough technology level to cloak.", thrown.getMessage());


//need enough technology
        OnePlayerTurn turn1 = new OnePlayerTurn(0, 0, null, null,
                false, 4, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),cloak);


        IllegalArgumentException thrown1 = assertThrows(IllegalArgumentException.class,
                () -> cloakChecker.checkTurn(new HashMap<>(), null, turn1, playerResources));

        assertEquals("More technology consumed than available.", thrown1.getMessage());



        Resources playerResources2 = new Resources(20, 50, 1, 1);

        cloakChecker.checkTurn(new HashMap<>(), null, turn1, playerResources2);
    }

}