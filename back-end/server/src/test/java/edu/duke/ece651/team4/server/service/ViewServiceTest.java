package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ViewServiceTest {

    @MockBean
    private SpyRepository spyRepository;

    @MockBean
    private TerritoryRepository territoryRepository;


    @MockBean
    private UnitRepository unitRepository;

    @MockBean
    private TerritoryViewRepository territoryViewRepository;

    @MockBean
    private UnitViewRepository unitViewRepository;

    @MockBean
    private NeighborRepository neighborRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @Autowired
    private ViewService viewService;

    @Test
    public void test_update_one_view_not_existinge() {
        Territory nT = new Territory(1, 15, "Duke", 10, 0);
        nT.setId(456);
        List<Unit> units = new ArrayList<>();
        Unit unit0 = new Unit(0, 1, 456);
        Unit unit1 = new Unit(1, 1, 456);
        Unit unit2 = new Unit(2, 1, 456);
        units.add(unit0);
        units.add(unit1);
        units.add(unit2);
        when(unitRepository.findByTerritoryId(456)).thenReturn(units);
        when(territoryViewRepository.findByTerritoryIdAndPlayerId(456, 1)).thenReturn(Optional.ofNullable(null));
        TerritoryView tView = new TerritoryView(456, 2, 1);
        tView.setId(45);
        when(territoryViewRepository.save(any())).thenReturn(tView);
        viewService.updateOneView(nT, 1);
    }

    @Test
    public void test_update_one_view_existing() {
        Territory nT = new Territory(1, 15, "Duke", 10, 0);
        nT.setId(456);
        List<Unit> units = new ArrayList<>();
        Unit unit0 = new Unit(0, 1, 456);
        Unit unit1 = new Unit(1, 1, 456);
        Unit unit2 = new Unit(2, 1, 456);
        units.add(unit0);
        units.add(unit1);
        units.add(unit2);
        when(unitRepository.findByTerritoryId(456)).thenReturn(units);
        TerritoryView tView = new TerritoryView(456, 2, 1);
        tView.setId(45);
        when(territoryViewRepository.findByTerritoryIdAndPlayerId(456, 1)).thenReturn(Optional.ofNullable(tView));
        UnitView uView = new UnitView(0, 1, 456);
        uView.setId(34);
        when(unitViewRepository.findByTerritoryViewIdAndType(anyInt(), anyInt())).thenReturn(uView);
        viewService.updateOneView(nT, 1);
    }

    @Test
    public void test_update_spies() {
        int playerID = 4;
        int tID = 456;
        int spyID = 9;
        Set<Integer> tIDs = new HashSet<>();
        List<Spy> spies = new ArrayList<>();
        Spy spy = new Spy(playerID, tID);
        spy.setId(spyID);
        spies.add(spy);
        when(spyRepository.findByPlayerId(playerID)).thenReturn(spies);
        Territory duke = new Territory(3, 1, "Duke", 0, 10);
        duke.setId(456);
        when(territoryRepository.findById(tID)).thenReturn(Optional.of(duke));
        List<Unit> units = new ArrayList<>();
        Unit unit0 = new Unit(0, 1, tID);
        Unit unit1 = new Unit(1, 1, tID);
        Unit unit2 = new Unit(2, 1, tID);
        units.add(unit0);
        units.add(unit1);
        units.add(unit2);
        when(unitRepository.findByTerritoryId(tID)).thenReturn(units);
        when(territoryViewRepository.findByTerritoryIdAndPlayerId(tID, playerID)).thenReturn(Optional.ofNullable(null));
        TerritoryView tView = new TerritoryView(tID, 2, playerID);
        tView.setId(45);
        when(territoryViewRepository.save(any())).thenReturn(tView);
        viewService.updateSpies(playerID, tIDs);
    }

    @Test
    public void test_all_views() {
        int gameID = 4;
        int playerID = 54;
        int uncID = 123;
        Game game = new Game(3);
        game.setId(4);
        List<Player> players = new ArrayList<>();
        Player player = new Player(4, 234, "Red");
        player.setId(playerID);
        players.add(player);
        when(playerRepository.findByGameId(gameID)).thenReturn(players);
        List<Territory> territories = new ArrayList<>();
        Territory unc = new Territory(54, 4, "UNC", 0, 10);
        unc.setId(uncID);
        territories.add(unc);
        when(territoryRepository.findByOwnerId(54)).thenReturn(territories);
        when(spyRepository.findByPlayerId(playerID)).thenReturn(new ArrayList<>());
        List<Neighbor> ns = new ArrayList<>();
        Neighbor uncNeighbor = new Neighbor(12, 54, 3);
        uncNeighbor.setId(4);
        ns.add(uncNeighbor);
        when(neighborRepository.findByNeighborId(uncID)).thenReturn(ns);
        when(territoryRepository.findById(12)).thenReturn(Optional.of(unc));
        when(territoryViewRepository.findByTerritoryIdAndPlayerId(anyInt(), anyInt())).thenReturn(Optional.ofNullable(null));
        TerritoryView tView = new TerritoryView(2, 2, 2);
        tView.setId(1);
        when(territoryViewRepository.save(any())).thenReturn(tView);
        viewService.updateAllViews(gameID);
    }

    @Test
    public void update_no_spies() {
        int playerID = 4;
        int tID = 456;
        int spyID = 9;
        Set<Integer> tIDs = new HashSet<>();
        tIDs.add(tID);
        List<Spy> spies = new ArrayList<>();
        Spy spy = new Spy(playerID, tID);
        spy.setId(spyID);
        spies.add(spy);
        when(spyRepository.findByPlayerId(playerID)).thenReturn(spies);
    }

}