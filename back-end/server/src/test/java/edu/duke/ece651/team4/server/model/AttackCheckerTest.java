package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.service.GameService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

class AttackCheckerTest {

    @Test
    void checkMyRule() {
        AttackChecker attackChecker = new AttackChecker(null);
        HashMap<String, List<Unit>> units = new HashMap<>();
        ArrayList<Unit> listUnits = new ArrayList<>();
        listUnits.add(new Unit(0, 3, 4));
        listUnits.add(new Unit(1, 0, 4));
        listUnits.add(new Unit(2, 4, 4));
        listUnits.add(new Unit(3, 0, 4));
        listUnits.add(new Unit(4, 3, 4));
        listUnits.add(new Unit(5, 4, 4));
        listUnits.add(new Unit(6, 0, 4));
        String[] ts = {"Northwestern", "Illinois", "Alabama", "Auburn", "Tennessee", "UVA", "Florida",
                "Georgia", "UNC", "South Carolina", "Duke", "NC State", "Maryland", "Villanova",
                "UPenn", "UConn", "Syracuse", "Penn State"};
        for (String tName : ts) {
            units.put(tName, listUnits);
        }
        GameService gameService = new GameService();
        HashMap<String, HashMap<String, Integer>> adjacency = gameService.createTerritories();
        String[] removeTs = {"Michigan", "Michigan State", "Indiana", "Ohio State", "Kentucky", "WVU"};
        for (String r : removeTs) {
            adjacency.remove(r);
        }
        Resources playerResources = new Resources(80, 40, 1.0, 1.0);
        List<Order> attacks = new ArrayList<>();
        List<Order> moves = new ArrayList<>();
        List<edu.duke.ece651.team4.server.model.Unit> unitsToAttack = new ArrayList<>();
        unitsToAttack.add(new edu.duke.ece651.team4.server.model.Unit(0, 1));
        unitsToAttack.add(new edu.duke.ece651.team4.server.model.Unit(5, 2));
        attacks.add(new Order("Northwestern", "Michigan", unitsToAttack));
        attacks.add(new Order("Syracuse", "Michigan State", unitsToAttack));
        OnePlayerTurn turn = new OnePlayerTurn(45, 2, moves, attacks,
                false, 0, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        attackChecker.checkMyRule(units, adjacency, turn, playerResources);
        assertEquals(14, playerResources.getFoodResources());
        assertEquals(40, playerResources.getTechnologyResources());
        List<Unit> northwesternUnits = units.get("Northwestern");
        for (Unit u : northwesternUnits) {
            if (u.getType() == 0) {
                assertEquals(u.getCount(), 1);
            }
            if (u.getType() == 5) {
                assertEquals(u.getCount(), 0);
            }
        }
    }

    @Test
    void checkAttackInvalid() {
        AttackChecker attackChecker = new AttackChecker(null);
        HashMap<String, List<Unit>> units = new HashMap<>();
        ArrayList<Unit> listUnits = new ArrayList<>();
        listUnits.add(new Unit(0, 3, 4));
        listUnits.add(new Unit(1, 0, 4));
        listUnits.add(new Unit(2, 4, 4));
        listUnits.add(new Unit(3, 0, 4));
        listUnits.add(new Unit(4, 3, 4));
        listUnits.add(new Unit(5, 4, 4));
        listUnits.add(new Unit(6, 0, 4));
        String[] ts = {"Northwestern", "Illinois", "Alabama", "Auburn", "Tennessee", "UVA", "Florida",
                "Georgia", "UNC", "South Carolina", "Duke", "NC State", "Maryland", "Villanova",
                "UPenn", "UConn", "Syracuse", "Penn State"};
        for (String tName : ts) {
            units.put(tName, listUnits);
        }
        GameService gameService = new GameService();
        HashMap<String, HashMap<String, Integer>> adjacency = gameService.createTerritories();
        String[] removeTs = {"Michigan", "Michigan State", "Indiana", "Ohio State", "Kentucky", "WVU"};
        for (String r : removeTs) {
            adjacency.remove(r);
        }
        // invalid non adjacent territories
        List<Order> attacks = new ArrayList<>();
        List<Order> moves = new ArrayList<>();
        List<edu.duke.ece651.team4.server.model.Unit> unitsToAttack = new ArrayList<>();
        unitsToAttack.add(new edu.duke.ece651.team4.server.model.Unit(0, 1));
        unitsToAttack.add(new edu.duke.ece651.team4.server.model.Unit(5, 2));
        attacks.add(new Order("Syracuse", "Michigan", unitsToAttack));
        OnePlayerTurn turn = new OnePlayerTurn(45, 2, moves, attacks,
                false, 0, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        Resources playerResources = new Resources(80, 40, 1.0, 1.0);
        assertThrows(IllegalArgumentException.class, () -> attackChecker.checkMyRule(units,
                adjacency, turn, playerResources));
        attacks.remove(0);
        unitsToAttack.add(new edu.duke.ece651.team4.server.model.Unit(3, 1));
        attacks.add(new Order("Syracuse", "Michigan State", unitsToAttack));
        assertThrows(IllegalArgumentException.class, () -> attackChecker.checkMyRule(units,
                adjacency, turn, playerResources));
        unitsToAttack.remove(2);
        attacks.remove(0);
        unitsToAttack = new ArrayList<>();
        unitsToAttack.add(new edu.duke.ece651.team4.server.model.Unit(0, 1));
        attacks.add(new Order("Syracuse", "Penn State", unitsToAttack));
        assertThrows(IllegalArgumentException.class, () -> attackChecker.checkMyRule(units, adjacency, turn, playerResources));
    }
}