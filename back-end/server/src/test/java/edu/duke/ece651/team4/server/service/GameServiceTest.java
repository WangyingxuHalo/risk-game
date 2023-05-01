package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.entity.Season;
import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.model.*;
import edu.duke.ece651.team4.server.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class GameServiceTest {

    @MockBean
    ViewService viewService;

    @MockBean
    SeasonService seasonService;

    @MockBean
    TwentySidedDice dice;

    @MockBean
    ActionService actionService;

    @Autowired
    GameService gameService;

    @MockBean
    GameRepository gameRepository;

    @MockBean
    PlayerRepository playerRepository;

    @MockBean
    TerritoryRepository territoryRepository;

    @MockBean
    NeighborRepository neighborRepository;

    @MockBean
    UnitRepository unitRepository;

    @MockBean
    ResolveRepository resolveRepository;

    @MockBean
    ResolveDetailRepository resolveDetailRepository;

    @MockBean
    SeasonRepository seasonRepository;

    @MockBean
    TerritoryViewRepository territoryViewRepository;

    @MockBean
    SpyRepository spyRepository;

    @MockBean
    UnitViewRepository unitViewRepository;

    @Test
    void addGame() {
    }

    @Test
    void testNeighbors() {
        HashMap<String, HashMap<String, Integer>> territoryNeighbors = gameService.createTerritories();
        HashSet<String> uniqueT = new HashSet<String>();
        for (String t : territoryNeighbors.keySet()) {
            uniqueT.add(t);
            for (String n : territoryNeighbors.get(t).keySet()) {
                uniqueT.add(n);
                Integer dist = territoryNeighbors.get(t).get(n);
                assertTrue(territoryNeighbors.get(n).containsKey(t));
                assertEquals(dist, territoryNeighbors.get(n).get(t));
            }
        }
        assertEquals(uniqueT.size(), 24);
    }

    @Test
    void testaddTerritoryNeighbors() {
        HashMap<String, Integer> keys = new HashMap<>();
        String[] ts = {"Michigan", "Michigan State", "Indiana", "Ohio State", "Kentucky", "WVU",
                "Northwestern", "Illinois", "Alabama", "Auburn", "Tennessee", "UVA", "Florida",
                "Georgia", "UNC", "South Carolina", "Duke", "NC State", "Maryland", "Villanova",
                "UPenn", "UConn", "Syracuse", "Penn State"};
        for (int i = 0; i < ts.length; i++) {
            keys.put(ts[i], i);
        }
        HashMap<String, HashMap<String, Integer>> territoryNeighbors = gameService.createTerritories();
        gameService.addTerritoryNeighbors(territoryNeighbors, keys);
        verify(neighborRepository, times(76)).save(any());
    }

    @Test
    void testCreateGame() {
        Game game = new Game(2);
        game.setId(1);
        Player red = new Player(1, 45,
                "red");
        red.setId(4);
        Territory t = new Territory();
        t.setId(4);
        when(territoryRepository.save(any())).thenReturn(t);
        when(playerRepository.save(any())).thenReturn(red);
        when(gameRepository.save(any())).thenReturn(game);
        gameService.createGame(45, 2);
        verify(playerRepository, times(2)).save(any());
        verify(territoryRepository, times(24)).save(any());
    }

    @Test
    void testGetTurn() {
        Game game = new Game();
        game.setTurnNum(1);
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));
        ResGetTurn resGetTurn = gameService.getTurn(1);
        Assertions.assertEquals(1, resGetTurn.getTurn());
    }

    @Test
    void testJoinGame() {
        List<Player> playerList = new ArrayList<>();
        when(playerRepository.findByGameIdAndUserId(1, null)).thenReturn(playerList);

        assertEquals(-1, gameService.joinGame(1, 1).getPlayerID());

        List<Player> playerList2 = new ArrayList<>();
        Player player = new Player();
        player.setId(1);
        player.setName("blue");
        playerList2.add(player);
        when(playerRepository.findByGameIdAndUserId(2, null)).thenReturn(playerList2);

        assertEquals(1, gameService.joinGame(1, 2).getPlayerID());
        assertEquals("blue", gameService.joinGame(1, 2).getColor());
    }

    @Test
    void testResumeGame() {
        when(playerRepository.findByIdAndGameId(1, 1)).thenReturn(null);
        assertFalse(gameService.resumeGame(1, 1));

        when(playerRepository.findByIdAndGameId(1, 2)).thenReturn(new Player());
        assertTrue(gameService.resumeGame(2, 1));
    }

    @Test
    void testGetGames() {
        when(playerRepository.findByUserId(1)).thenReturn(new ArrayList<>());
        Game game1 = new Game();
        game1.setGameOver(false);
        Game game2 = new Game();
        game2.setGameOver(false);
        when(gameRepository.findById(1)).thenReturn(Optional.of(game1), Optional.of(game2));

        List<ResGame> resGames = gameService.getGames(1).getGames();
        assertEquals(0, resGames.size());


        List<Player> playerList = new ArrayList<>();
        Player player = new Player(1, 2, "blue");
        player.setId(1);
        player.setGameId(1);
        playerList.add(player);
        when(playerRepository.findByUserId(2)).thenReturn(playerList);

        List<ResGame> resGames2 = gameService.getGames(2).getGames();
        assertEquals(1, resGames2.size());
        assertEquals(1, resGames2.get(0).getGameID());
        assertEquals(1, resGames2.get(0).getPlayerID());
        assertEquals("blue", resGames2.get(0).getColor());

    }

    @Test
    void testGetGameMap() {
        Player currentPlayer = new Player();
        currentPlayer.setId(1);
        currentPlayer.setGameId(1);
        currentPlayer.setName("red");
        when(playerRepository.findById(1)).thenReturn(Optional.of(currentPlayer));

        Player player2 = new Player();
        player2.setId(2);
        player2.setName("blue");

        Player player3 = new Player();
        player3.setId(3);
        player3.setName("green");

        Player player4 = new Player();
        player4.setId(4);
        player4.setName("yellow");

        List<Player> players = new ArrayList<>();
        players.add(currentPlayer);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        when(playerRepository.findByGameId(1)).thenReturn(players);

        List<Territory> territories = new ArrayList<>();
        Territory territory1 = new Territory();
        territory1.setId(1);
        territory1.setOwnerId(1);
        territory1.setName("t1");
        Territory territory2 = new Territory();
        territory2.setId(2);
        territory2.setOwnerId(2);
        territory2.setName("t2");
        Territory territory3 = new Territory();
        territory3.setId(3);
        territory3.setOwnerId(3);
        territory3.setName("t3");
        territory3.setCloak(0);
        Territory territory4 = new Territory();
        territory4.setId(4);
        territory4.setOwnerId(4);
        territory4.setName("t4");
        territory4.setCloak(1);
        Territory territory5 = new Territory();
        territory5.setId(5);
        territory5.setOwnerId(1);
        territory5.setName("t5");
        Territory territory6 = new Territory();
        territory6.setId(6);
        territory6.setOwnerId(2);
        territory6.setName("t6");
        territory6.setCloak(0);
        territories.add(territory1);
        territories.add(territory2);
        territories.add(territory3);
        territories.add(territory4);
        territories.add(territory5);
        territories.add(territory6);
        when(territoryRepository.findByGameId(1)).thenReturn(territories);

        List<Unit> units1 = new ArrayList<>();
        Unit unit1 = new Unit(1, 1, territory1.getId());
        units1.add(unit1);
        when(unitRepository.findByTerritoryId(territory1.getId())).thenReturn(units1);

        List<Unit> units2 = new ArrayList<>();
        Unit unit2 = new Unit(1, 1, territory2.getId());
        units2.add(unit2);
        when(unitRepository.findByTerritoryId(territory2.getId())).thenReturn(units2);

        GameService gameServiceSpy = spy(gameService);
        List<Spy> spies = new ArrayList<>();
        spies.add(new Spy());
        doReturn(spies).when(spyRepository).findByPlayerIdAndTerritoryId(1, 2);
        doReturn(true).when(gameServiceSpy).isAdjacent(1, 3);
        doReturn(true).when(gameServiceSpy).isAdjacent(1, 4);
        doReturn(false).when(gameServiceSpy).isAdjacent(1, 5);
        doReturn(false).when(gameServiceSpy).isAdjacent(1, 6);

        TerritoryView territoryView = new TerritoryView();
        territoryView.setId(1);
        doReturn(Optional.of(territoryView)).when(territoryViewRepository).findByTerritoryIdAndPlayerId(anyInt(), anyInt());

        List<UnitView> unitViews = new ArrayList<>();
        UnitView unitView = new UnitView(1, 1, 1);
        unitViews.add(unitView);
        doReturn(unitViews).when(unitViewRepository).findByTerritoryViewId(anyInt());

        ResGameMap gameMap = gameServiceSpy.getGameMap(1);
        assertNotNull(gameMap);
        assertEquals(60, gameMap.getMaxUnits());
        assertEquals(6, gameMap.getTerritories().size());
        assertTrue(gameMap.getTerritories().containsKey("t1"));
        assertTrue(gameMap.getTerritories().containsKey("t2"));
        assertTrue(gameMap.getTerritories().containsKey("t3"));
        assertTrue(gameMap.getTerritories().containsKey("t4"));
        assertTrue(gameMap.getTerritories().containsKey("t5"));
        assertTrue(gameMap.getTerritories().containsKey("t6"));
    }

    @Test
    void testIsAdjacent() {
        Territory territory1 = new Territory();
        territory1.setId(1);
        doReturn(Collections.singletonList(territory1)).when(territoryRepository).findByOwnerId(1);
        doReturn(Collections.singletonList(new Neighbor())).when(neighborRepository).findByTerritoryIdAndNeighborId(1, 1);
        assertTrue(gameService.isAdjacent(1, 1));

        Territory territory2 = new Territory();
        territory2.setId(2);
        doReturn(Collections.singletonList(territory2)).when(territoryRepository).findByOwnerId(2);
        doReturn(new ArrayList<>()).when(neighborRepository).findByTerritoryIdAndNeighborId(2, 2);
        assertFalse(gameService.isAdjacent(2, 2));
    }

    @Test
    void testIsGameReady() {
        when(playerRepository.findByGameIdAndUserId(1, null)).thenReturn(new ArrayList<>());
        assertTrue(gameService.isGameReady(1));

        List<Player> players = new ArrayList<>();
        players.add(new Player());
        when(playerRepository.findByGameIdAndUserId(2, null)).thenReturn(players);
        assertFalse(gameService.isGameReady(2));
    }

    @Test
    void testGetAllInfo() {
        GameService gameServiceSpy = spy(gameService);
        ResGameMap resGameMap = new ResGameMap();
        doReturn(resGameMap).when(gameServiceSpy).getGameMap(1);

        Player player = new Player();
        player.setId(1);
        player.setName("blue");
        player.setGameId(1);
        player.setTotalFood(10);
        player.setTotalTech(10);
        player.setAlive(true);
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        List<Player> players = new ArrayList<>();
        Player player1 = new Player();
        player1.setAlive(false);
        players.add(player1);
        players.add(player);
        when(playerRepository.findByGameId(1)).thenReturn(players);

        Game game = new Game();
        game.setId(1);
        game.setNumAlivePlayers(1);
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        List<Territory> territories = new ArrayList<>();
        Territory territory = new Territory();
        territory.setFoodGeneration(10);
        territory.setTechGeneration(10);
        territories.add(territory);
        when(territoryRepository.findByOwnerId(1)).thenReturn(territories);

        Season season = new Season();
        season.setSeason("season");
        season.setMessage("message");
        when(seasonRepository.findByGameId(1)).thenReturn(season);

        assertNotNull(gameServiceSpy.getAllInfo(1));

        verify(gameServiceSpy, times(1)).getGameMap(1);
        verify(playerRepository, times(1)).findById(1);
        verify(playerRepository, times(1)).findByGameId(1);
        verify(gameRepository, times(1)).findById(1);
        verify(territoryRepository, times(1)).findByOwnerId(1);
        verify(seasonRepository, times(1)).findByGameId(1);
    }

    @Test
    void testGetResResolve() {
        List<Resolve> resolves = new ArrayList<>();
        Resolve resolve = new Resolve();
        resolve.setId(1);
        resolve.setOriginalOwnerId(1);
        resolve.setWinnerId(1);
        resolve.setAttackedTerritoryId(1);
        resolves.add(resolve);
        resolves.add(resolve);
        when(resolveRepository.findByGameId(1)).thenReturn(resolves);
        Territory territory = new Territory();
        territory.setName("t");
        when(territoryRepository.findById(1)).thenReturn(Optional.of(territory));
        Player player = new Player();
        player.setName("p");
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        List<ResolveDetail> resolveDetails = new ArrayList<>();
        ResolveDetail resolveDetail = new ResolveDetail();
        resolveDetail.setAttackerId(1);
        resolveDetail.setTerritoryId(1);
        resolveDetails.add(resolveDetail);
        resolveDetails.add(resolveDetail);
        when(resolveDetailRepository.findByResolveId(1)).thenReturn(resolveDetails);

        gameService.getResResolve(1);
    }

    @Test
    void testBonus() {
        Player p = new Player(45, 1, "Red");
        p.setId(3);
        when(playerRepository.findById(3)).thenReturn(Optional.of(p));
        when(dice.roll()).thenReturn(16);
        assertEquals(p.getTotalFood(), 40);
        Bonus foodBonus = new Bonus("Food", null, 3);
        gameService.playBonus(foodBonus);
        verify(playerRepository, times(1)).save(any());
        verify(actionService, times(1)).conductTurn(any());
        Bonus techBonus = new Bonus("Tech", null, 3);
        gameService.playBonus(techBonus);
        verify(playerRepository, times(2)).save(any());
        verify(actionService, times(2)).conductTurn(any());
        Bonus terrBonus = new Bonus("Territory", "Duke", 3);
        Territory duke = new Territory(3, 45, "Duke", 10, 0);
        duke.setId(445);
        when(territoryRepository.findByNameAndOwnerId("Duke", 3)).thenReturn(duke);
        when(unitRepository.findByTerritoryIdAndType(445,
                0)).thenReturn(new Unit(0, 6, 445));
        gameService.playBonus(terrBonus);
        verify(playerRepository, times(2)).save(any());
        verify(actionService, times(3)).conductTurn(any());
    }
}
