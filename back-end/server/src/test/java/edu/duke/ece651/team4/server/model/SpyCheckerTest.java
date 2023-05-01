package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.service.GameService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpyCheckerTest {


    /**
     *     @Override
    protected void checkMyRule(HashMap<String, List<Unit>> units,
                               HashMap<String, HashMap<String, Integer>> neighbors,
                               OnePlayerTurn turn,
                               Resources playerResources) {
        upgradeSpy(turn, units, playerResources);
        moveSpy(turn, playerResources, neighbors);

    }
    
     */

//    @Autowired
//    GameService gameService;
    @Test
    void check_my_rule() {
        SpyChecker spyChecker = new SpyChecker(null);


        HashMap<String, List<Unit>> units = new HashMap<>();


        HashMap<String, HashMap<String, Integer>> adjacency = new HashMap<>();
        HashMap<String, Integer> dukeDist = new HashMap<>();

        dukeDist.put("UNC", 4);
        dukeDist.put("Kentucky", 3);
        adjacency.put("Duke", dukeDist);
        HashMap<String, Integer> uncDist = new HashMap<>();
        uncDist.put("Duke", 4);
        uncDist.put("Kentucky", 7);
        adjacency.put("UNC", uncDist);
        HashMap<String, Integer> kentuckyDist = new HashMap<>();
        kentuckyDist.put("Duke", 3);
        kentuckyDist.put("UNC", 7);
        adjacency.put("Kentucky", kentuckyDist);
        
        ArrayList<Unit> listUnits = new ArrayList<>();
        listUnits.add(new Unit(0, 1, 4));
        listUnits.add(new Unit(1, 0, 4));
  

        units.put("Duke", listUnits);
        List<SpyUpgrade> spyUpgrade1 = new ArrayList<>();
        SpyUpgrade upgrade1 = new SpyUpgrade(1,"ABC");
        spyUpgrade1.add(upgrade1);
        OnePlayerTurn turn = new OnePlayerTurn(0, 0, new ArrayList<>(), null,
                false, 0, new ArrayList<>(),new ArrayList<>(),spyUpgrade1,new ArrayList<>());

        Resources playerResources = new Resources(10, 10, 1.0, 1.0);


        //Player must own the territory in which it is upgrading a spy. Player does not own territory ABC.
        assertThrows(IllegalArgumentException.class,
                           () -> spyChecker.upgradeSpy(turn,units,playerResources));



        List<SpyUpgrade> spyUpgrade2 = new ArrayList<>();
        SpyUpgrade upgrade2 = new SpyUpgrade(1,"Duke");
        spyUpgrade2.add(upgrade2);
        OnePlayerTurn turn2 = new OnePlayerTurn(0, 0, new ArrayList<>(), null,
                false, 0, new ArrayList<>(),new ArrayList<>(),spyUpgrade2,new ArrayList<>());

       // spyChecker.upgradeSpy(turn2,units,playerResources);
//You cannot upgrade 1 in territory Duke because there are not that many level 1 units.

        assertThrows(IllegalArgumentException.class,
                () -> spyChecker.upgradeSpy(turn2,units,playerResources));



        List<SpyUpgrade> spyUpgrade3 = new ArrayList<>();
        SpyUpgrade upgrade3 = new SpyUpgrade(1,"Duke");
        spyUpgrade3.add(upgrade3);
        OnePlayerTurn turn3 = new OnePlayerTurn(0, 0, new ArrayList<>(), null,
                false, 0, new ArrayList<>(),new ArrayList<>(),spyUpgrade2,new ArrayList<>());

        ArrayList<Unit> listUnits2 = new ArrayList<>();
        listUnits2.add(new Unit(0, 1, 4));
        listUnits2.add(new Unit(1, 1, 4));

        HashMap<String, List<Unit>> units2 = new HashMap<>();

        units2.put("Duke", listUnits2);


       // More technology consumed than available.
        assertThrows(IllegalArgumentException.class,
                () -> spyChecker.upgradeSpy(turn3,units2,playerResources));


        SpyMove spymove1 = new SpyMove("Duke", "ABC",1);
        ArrayList<SpyMove> move = new ArrayList<>();
        move.add(spymove1);

        OnePlayerTurn turn4 = new OnePlayerTurn(0, 0, new ArrayList<>(), null,
                false, 0, new ArrayList<>(),move,new ArrayList<>(),new ArrayList<>());

        //Spy moves must be from adjacent territories. Territories Duke and ABC are not adjacent.
        assertThrows(IllegalArgumentException.class,
                () -> spyChecker.moveSpy(turn4,playerResources,adjacency));


        SpyMove spymove2 = new SpyMove("Duke", "UNC",1);
        ArrayList<SpyMove> move2 = new ArrayList<>();
        move2.add(spymove2);
        Resources playerResources2 = new Resources(100, 100, 1.0, 1.0);
        OnePlayerTurn turn5 = new OnePlayerTurn(0, 0, new ArrayList<>(), null,
                false, 0, new ArrayList<>(),move2,new ArrayList<>(),new ArrayList<>());

        spyChecker.moveSpy(turn5,playerResources2,adjacency);
        spyChecker.checkMyRule(units2,adjacency,turn5,playerResources2);
    }



}