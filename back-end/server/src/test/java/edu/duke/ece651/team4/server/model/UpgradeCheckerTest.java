package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.service.GameService;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpgradeCheckerTest {

//    @Autowired
//    GameService gameService;
    @Test
    void check_my_rule() {
        UpgradeChecker upgradeChecker = new UpgradeChecker(null);
        HashMap<String, List<Unit>> units = new HashMap<>();
        ArrayList<Unit> listUnits = new ArrayList<>();
        listUnits.add(new Unit(0, 100, 4));
        units.put("Duke", listUnits);
        Resources playerResources = new Resources(20, 20, 1, 1);

        //Units level should always be positive
        List<Upgrade> upgrades = new ArrayList<>();
        upgrades.add(new Upgrade(10,1,-4,"Duke"));
        OnePlayerTurn turn = new OnePlayerTurn(0, 0, null, null,
                false, 3, upgrades,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());


        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> upgradeChecker.checkTurn(units, null, turn, playerResources));

        assertEquals("Units level should always be positive.", thrown.getMessage());


        //Units can only be upgraded.

        List<Upgrade> upgrades1 = new ArrayList<>();
        upgrades1.add(new Upgrade(10,3,1,"Duke"));
        OnePlayerTurn turn1 = new OnePlayerTurn(0, 0, null, null,
                false, 1, upgrades1,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());


        IllegalArgumentException thrown1 = assertThrows(IllegalArgumentException.class,
                () -> upgradeChecker.checkTurn(units, null, turn1, playerResources));

        assertEquals("Units can only be upgraded.", thrown1.getMessage());



        //You does not have enough technology level to upgrade the units, please do research first
        List<Upgrade> upgrades2 = new ArrayList<>();
        upgrades2.add(new Upgrade(10,1,3,"Duke"));
        OnePlayerTurn turn2 = new OnePlayerTurn(0, 0, null, null,
                false, 1, upgrades2,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class,
                () -> upgradeChecker.checkTurn(units, null, turn2, playerResources));

        assertEquals("You does not have enough technology level to upgrade the units, please do research first.", thrown2.getMessage());


//Player must own the territories to upgrade the unit

        List<Upgrade> upgrades3 = new ArrayList<>();
        upgrades3.add(new Upgrade(10,1,3,"AAA"));
        OnePlayerTurn turn3 = new OnePlayerTurn(0, 0, null, null,
                false, 5, upgrades3,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class,
                () -> upgradeChecker.checkTurn(units, null, turn3, playerResources));

        assertEquals("Player must own the territories to upgrade the unit", thrown3.getMessage());


        List<Upgrade> upgrades4 = new ArrayList<>();
        upgrades4.add(new Upgrade(10,1,5,"Duke"));
        OnePlayerTurn turn4 = new OnePlayerTurn(0, 0, null, null,
                false, 5, upgrades4,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        IllegalArgumentException thrown4 = assertThrows(IllegalArgumentException.class,
                () -> upgradeChecker.checkTurn(units, null, turn4, playerResources));

        assertEquals("More technology consumed than available.", thrown4.getMessage());


        List<Upgrade> upgrades5 = new ArrayList<>();
        upgrades5.add(new Upgrade(1,0,1,"Duke"));
        OnePlayerTurn turn5 = new OnePlayerTurn(0, 0, null, null,
                false, 5, upgrades5,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        Resources playerResources1 = new Resources(20, 20, 1, 1);
        upgradeChecker.checkTurn(units, null, turn5, playerResources1);

    }


    @Test
    void updateUnits_test() {
        UpgradeChecker upgradeChecker = new UpgradeChecker(null);


        HashMap<String, List<Unit>> units = new HashMap<>();
        ArrayList<Unit> listUnits = new ArrayList<>();
        listUnits.add(new Unit(0, 100, 4));
        listUnits.add(new Unit(2, 10, 4));

        units.put("Duke", listUnits);

        Upgrade p =new Upgrade(110,0,2,"Duke");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> upgradeChecker.updateUnits(units,p));

        assertEquals("Player does not have enough units at level 0 to upgrade.", thrown.getMessage());

        Upgrade p1 =new Upgrade(10,1,2,"Duke");

        IllegalArgumentException thrown1 = assertThrows(IllegalArgumentException.class,
                () -> upgradeChecker.updateUnits(units,p1));

        assertEquals("Player does not have any level1 unit.", thrown1.getMessage());

        Upgrade p2 =new Upgrade(10,0,2,"Duke");

        upgradeChecker.updateUnits(units,p2);


    }

}