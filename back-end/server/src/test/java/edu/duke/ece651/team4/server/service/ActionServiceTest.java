package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.entity.Season;
import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.model.*;
import edu.duke.ece651.team4.server.repository.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class ActionServiceTest {
    @MockBean
    SpyRepository spyRepository;
    @MockBean
    SeasonRepository seasonRepository;
    @MockBean
    PlayerRepository playerRepository;

    @Autowired
    ActionService actionService;

    @MockBean
    AttackService attackService;

    @MockBean
    TerritoryRepository territoryRepository;

    @MockBean
    NeighborRepository neighborRepository;

    @MockBean
    EntityManager entityManager;

    @MockBean
    private UnitRepository unitRepository;

    @MockBean
    private PlayerOrderRepository playerOrderRepository;

    @MockBean
    private AttackRepository attackRepository;

    @Test
    void conductTurn() {
    }

    @Test
    void getUnits() {
        List<Territory> territories = new ArrayList<>();
        Territory duke = new Territory(45, 0, "Duke", 0, 0);
        duke.setId(1);
        Territory unc = new Territory(45, 0, "UNC", 0, 0);
        unc.setId(2);
        Territory kentucky = new Territory(45, 0, "Kentucky", 0, 0);
        kentucky.setId(3);
        territories.add(duke);
        territories.add(unc);
        territories.add(kentucky);
        when(territoryRepository.findByOwnerId(anyInt())).thenReturn(territories);
        Player p = new Player(1, 3, "Red");
        p.setId(45);
        when(playerRepository.findById(anyInt())).thenReturn(Optional.of(p));
        ArrayList<Unit> listUnits = new ArrayList<>();
        listUnits.add(new Unit(0, 10, 4));
        listUnits.add(new Unit(1, 0, 4));
        listUnits.add(new Unit(2, 0, 4));
        listUnits.add(new Unit(3, 0, 4));
        listUnits.add(new Unit(4, 0, 4));
        listUnits.add(new Unit(5, 0, 4));
        listUnits.add(new Unit(6, 0, 4));
        PlayerOrder pOrder = new PlayerOrder(0, 45, false, 3);
        pOrder.setId(102);
        when(playerOrderRepository.save(any())).thenReturn(pOrder);
        when(unitRepository.findByTerritoryId(anyInt())).thenReturn(listUnits);
        when(territoryRepository.findById(eq(1))).thenReturn(Optional.of(duke));
        when(territoryRepository.findById(eq(2))).thenReturn(Optional.of(unc));
        when(territoryRepository.findById(eq(3))).thenReturn(Optional.of(kentucky));
        when(attackService.conductAttack(anyInt())).thenReturn(false);
        List<Neighbor> ns = new ArrayList<>();
        Neighbor nDuke = new Neighbor(1, 1, 6);
        Neighbor nUNC = new Neighbor(2, 1, 6);
        Neighbor nKentucky = new Neighbor(3, 1, 6);
        ns.add(nDuke);
        ns.add(nUNC);
        ns.add(nKentucky);
        when(neighborRepository.findByNeighborId(anyInt())).thenReturn(ns);
        List<Order> orders = new ArrayList<>();
        List<edu.duke.ece651.team4.server.model.Unit> unitsMove = new ArrayList<>();
        unitsMove.add(new edu.duke.ece651.team4.server.model.Unit(0, 1));
        orders.add(new Order("Duke", "UNC", unitsMove));
        OnePlayerTurn turn = new OnePlayerTurn(0, 0, orders, new ArrayList<>(),
                false, 0, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        HashMap<String, List<Unit>> hash = actionService.getUnits(45);
        when(territoryRepository.findByNameAndOwnerId(anyString(), anyInt())).thenReturn(duke);
        when(unitRepository.findByTerritoryIdAndType(anyInt(),
                anyInt())).thenReturn(new Unit(0, 5, 4));
        when(seasonRepository.findByGameId(anyInt())).thenReturn(new Season(234,
                null, null, 0, 0, 1, 1));
        actionService.conductTurn(turn);
        unitsMove.add(new edu.duke.ece651.team4.server.model.Unit(5, 10));
        String ans = actionService.conductTurn(turn);
        assertEquals(ans, "Moves must not leave a player with negative units. Territory Duke has negative units.");
        unitsMove.remove(1);
        unitsMove.add(new edu.duke.ece651.team4.server.model.Unit(0, 8));
        String ansExpensive = actionService.conductTurn(turn);
        assertEquals(ansExpensive, "More food consumed than available.");
    }

    @Test
    void getNeighbors() {
    }



    @Test
    void executeResearch_test(){
        Player p = new Player(1, 3, "Red");
        when(playerRepository.findById(anyInt())).thenReturn(Optional.of(p));

        actionService.executeResearch(1, 10);
        assertEquals(2, p.getTechLevel());

        verify(playerRepository, times(1)).findById(anyInt());
        verify(playerRepository, times(1)).save(p);
    }

    @Test
    void executeUpgrade_test(){
        int playerID = 1;
        List<Upgrade> upgrades = new ArrayList<>();
        Territory territory1 = new Territory();
        territory1.setId(1);
        territory1.setName("Duke");
        upgrades.add(new Upgrade(110,0,2,"Duke"));
        upgrades.add(new Upgrade(110,0,2,"Duke"));

        Unit u1 = new Unit();
        u1.setCount(100);
        u1.setTerritoryId(1);
        u1.setType(0);
        Player p = new Player(1, 3, "Red");

        when(territoryRepository.findByNameAndOwnerId(anyString(), anyInt())).thenReturn(territory1);
        when(unitRepository.findByTerritoryIdAndType(anyInt(), anyInt())).thenReturn(u1);
        when(playerRepository.findById(anyInt())).thenReturn(Optional.of(p));

        actionService.executeUpdate(upgrades, 1);
        verify(territoryRepository, times(2)).findByNameAndOwnerId(anyString(), eq(playerID));
        verify(unitRepository, times(4)).save(any(Unit.class));
    }

    @Test
    public void test_save_attacks() {
        List<Order> orders = new ArrayList<>();
        List<edu.duke.ece651.team4.server.model.Unit> unitsAttack = new ArrayList<>();
        unitsAttack.add(new edu.duke.ece651.team4.server.model.Unit(0, 1));
        orders.add(new Order("Duke", "UNC", unitsAttack));
        OnePlayerTurn turn = new OnePlayerTurn(0, 0,
                new ArrayList<>(), orders, false, 1, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        Player red = new Player(234, 22, "Red");
        red.setId(1);
        when(playerRepository.save(any())).thenReturn(red);
        Territory tAns = new Territory(1, 234, "Duke", 0, 10);
        tAns.setId(4);
        when(territoryRepository.findByNameAndOwnerId(any(), anyInt())).thenReturn(tAns);
        when(territoryRepository.findByNameAndGameId(any(), anyInt())).thenReturn(tAns);
        Unit u = new Unit(0, 4, 4);
        u.setId(456);
        when(unitRepository.findByTerritoryIdAndType(anyInt(), anyInt())).thenReturn(u);
        PlayerOrder o = new PlayerOrder(1, 1, false, 234);
        o.setId(398);
        when(playerOrderRepository.save(any())).thenReturn(o);
        actionService.saveAttacks(turn, red);
    }

    @Test
    public void test_do_some_research() {
        OnePlayerTurn turn = new OnePlayerTurn(1, 0,
                new ArrayList<>(), new ArrayList<>(), true, 1, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        Player red = new Player(234, 22, "Red");
        red.setId(1);
        when(playerRepository.findById(1)).thenReturn(Optional.of(red));
        PlayerOrder order = new PlayerOrder(0, 1, true, 234);
        order.setId(4);
        when(playerOrderRepository.save(any())).thenReturn(order);
        when(seasonRepository.findByGameId(anyInt())).thenReturn(new Season(234,
                null, null, 0, 0, 1, 1));
        actionService.conductTurn(turn);
    }



    @Test
    public void test_do_some_cloak() {
        List<String> cloak = new ArrayList<>();
        cloak.add("duke");
        Territory t = new Territory(1, 0, null, 0, 0);
        when(territoryRepository.findByNameAndOwnerId("duke", 1)).thenReturn(t);
        when(territoryRepository.save(any())).thenReturn(t);
        actionService.executeCloak(cloak,1);
    }


    @Test
    public void test_removecloak() {
        List<String> cloak = new ArrayList<>();
        List<Territory> terrs = new ArrayList<>();
        cloak.add("duke");
        Territory t = new Territory(1, 1, null, 0, 0);
        Territory t2 = new Territory(1, 1, null, 0, 0);
        t2.setCloak(4);
        terrs.add(t);
        terrs.add(t2);

        when(territoryRepository.findByOwnerId(1)).thenReturn(terrs);
        when(territoryRepository.save(any())).thenReturn(t);
        actionService.removeCloak(1);
    }

    @Test
    public void test_execute_spy() {
        int playerID = 4;
        int gameID = 12;
        int dukeID = 1;
        int uncID = 2;
        List<SpyMove> sMoves = new ArrayList<>();
        SpyMove sMove = new SpyMove("Duke", "UNC", 1);
        sMoves.add(sMove);
        Territory Duke = new Territory(playerID, gameID, "Duke", 0, 0);
        Duke.setId(dukeID);
        Territory UNC = new Territory(playerID, gameID, "UNC", 0, 0);
        UNC.setId(uncID);
        when(territoryRepository.findByNameAndGameId("Duke", gameID)).thenReturn(Duke);
        when(territoryRepository.findByNameAndGameId("UNC", gameID)).thenReturn(UNC);
        List<Spy> spies = new ArrayList<>();
        Spy spy = new Spy(playerID, dukeID);
        spies.add(spy);
        when(spyRepository.findByPlayerIdAndTerritoryId(playerID, dukeID)).thenReturn(spies);
        actionService.executeSpyMoves(sMoves, playerID, gameID);
    }

    @Test
    public void test_check_spy_move() {
        int gameID = 4;
        int playerID = 4;
        int dukeID = 1;
        int uncID = 2;
        Territory Duke = new Territory(playerID, gameID, "Duke", 0, 0);
        Duke.setId(dukeID);
        Territory UNC = new Territory(playerID, gameID, "UNC", 0, 0);
        UNC.setId(uncID);
        Resources playerResources = new Resources(40, 40, 1, 1);
        SpyMove sMove = new SpyMove("Duke", "UNC", 1);
        when(territoryRepository.findByNameAndGameId("Duke", gameID)).thenReturn(Duke);
        when(territoryRepository.findByNameAndGameId("UNC", gameID)).thenReturn(UNC);
        List<Neighbor> neighbors = new ArrayList<>();
        when(neighborRepository.findByNeighborId(dukeID)).thenReturn(neighbors);
        assertThrows(IllegalArgumentException.class,
                () -> actionService.checkSpyMove(sMove, gameID, playerResources));

        Neighbor uncN = new Neighbor(uncID, dukeID, 2);
        neighbors.add(uncN);
        actionService.checkSpyMove(sMove, gameID, playerResources);
    }

    @Test
    public void test_check_spies() {
        int gameID = 4;
        int playerID = 4;
        int dukeID = 1;
        int uncID = 2;
        List<SpyMove> sMoves = new ArrayList<>();
        SpyMove sMove = new SpyMove("Duke", "UNC", 1);
        sMoves.add(sMove);
        OnePlayerTurn turn = new OnePlayerTurn(playerID, 0, null, null, false, 4, null, sMoves, null, null);
        Territory Duke = new Territory(playerID, gameID, "Duke", 0, 0);
        Duke.setId(dukeID);
        Territory UNC = new Territory(playerID, gameID, "UNC", 0, 0);
        UNC.setId(uncID);
        Resources playerResources = new Resources(40, 40, 1, 1);
        when(territoryRepository.findByNameAndGameId("Duke", gameID)).thenReturn(Duke);
        when(territoryRepository.findByNameAndGameId("UNC", gameID)).thenReturn(UNC);
        List<Neighbor> neighbors = new ArrayList<>();
        when(neighborRepository.findByNeighborId(dukeID)).thenReturn(neighbors);
        assertThrows(IllegalArgumentException.class,
                () -> actionService.checkSpyMove(sMove, gameID, playerResources));

        Neighbor uncN = new Neighbor(uncID, dukeID, 2);
        neighbors.add(uncN);
        assertThrows(IllegalArgumentException.class, () ->
                actionService.checkSpies(turn, gameID, playerResources));
        List<Spy> spies = new ArrayList<>();
        Spy spy = new Spy(playerID, dukeID);
        spies.add(spy);
        when(spyRepository.findByPlayerIdAndTerritoryId(playerID, dukeID)).thenReturn(spies);
        actionService.checkSpies(turn, gameID, playerResources);
    }

    @Test
    public void test_spy_upgrade() {
        int gameID = 4;
        int playerID = 4;
        int dukeID = 1;
        List<SpyUpgrade> sUpgrades = new ArrayList<>();
        SpyUpgrade spyUpgrade = new SpyUpgrade(1, "Duke");
        sUpgrades.add(spyUpgrade);
        Territory Duke = new Territory(playerID, gameID, "Duke", 0, 0);
        Duke.setId(dukeID);
        when(territoryRepository.findByNameAndOwnerId("Duke", playerID)).thenReturn(Duke);
        Unit u = new Unit(1, 5, dukeID);
        when(unitRepository.findByTerritoryIdAndType(dukeID, 1)).thenReturn(u);
        actionService.executeSpyUpgrade(sUpgrades, playerID);
    }

}