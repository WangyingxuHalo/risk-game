package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.service.GameService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveCheckerTest {

//    @Autowired
//    GameService gameService;
    @Test
    void check_my_rule() {
        MoveChecker moveChecker = new MoveChecker(null);
        HashMap<String, List<Unit>> units = new HashMap<>();
        ArrayList<Unit> listUnits = new ArrayList<>();
        listUnits.add(new Unit(0, 1, 4));
        listUnits.add(new Unit(1, 0, 4));
        listUnits.add(new Unit(2, 0, 4));
        listUnits.add(new Unit(3, 0, 4));
        listUnits.add(new Unit(4, 0, 4));
        listUnits.add(new Unit(5, 0, 4));
        listUnits.add(new Unit(6, 0, 4));
        units.put("Duke", listUnits);
        units.put("UNC", listUnits);
        units.put("Kentucky", listUnits);
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
        List<Order> orders = new ArrayList<>();
        List<edu.duke.ece651.team4.server.model.Unit> unitsMove = new ArrayList<>();
        unitsMove.add(new edu.duke.ece651.team4.server.model.Unit(0, 1));
        orders.add(new Order("Duke", "UNC", unitsMove));
        OnePlayerTurn turn = new OnePlayerTurn(0, 0, orders, null,
                false, 0, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        Resources playerResources = new Resources(20, 20, 1.0, 1.0);
        moveChecker.checkTurn(units, adjacency, turn, playerResources);
        orders.add(new Order("Michigan", "UNC", unitsMove));
        assertThrows(IllegalArgumentException.class,
                () -> moveChecker.checkTurn(units, adjacency, turn, playerResources));
        orders.remove(1);
        moveChecker.checkTurn(units, adjacency, turn, playerResources);
        unitsMove.add(new edu.duke.ece651.team4.server.model.Unit(1, 1));
        assertThrows(IllegalArgumentException.class,
                () -> moveChecker.checkTurn(units, adjacency, turn, playerResources));
    }

    @Test
    void test_shortest_path() {
        MoveChecker moveChecker = new MoveChecker(null);
        GameService gameService = new GameService();
        HashMap<String, HashMap<String, Integer>> adjacency = gameService.createTerritories();
        int ans = moveChecker.shortestPath(adjacency, "Maryland", "Alabama", adjacency.keySet());
        assertEquals(ans, 17);
        Set<String> owned = new HashSet<>();
        owned.add("UConn");
        owned.add("UPenn");
        owned.add("Villanova");
        owned.add("WVU");
        owned.add("Indiana");
        assertThrows(IllegalArgumentException.class,
                () -> moveChecker.shortestPath(adjacency, "UConn", "Indiana", owned));
        try {
            moveChecker.shortestPath(adjacency, "UConn", "Indiana", owned);
        }
        catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "No valid move path exists between UConn and Indiana.");
        }
    }
}