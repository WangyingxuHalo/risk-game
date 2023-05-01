package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class FinishTurnServiceTest {
    @MockBean
    ViewService viewService;

    @MockBean
    UnitRepository unitRepository;

    @MockBean
    GameRepository gameRepository;

    @MockBean
    PlayerRepository playerRepository;

    @MockBean
    TerritoryRepository territoryRepository;

    @MockBean
    SeasonRepository seasonRepository;

    @Autowired
    FinishTurnService finishTurnService;

    @Test
    void finishTurn() {
        Game game = new Game(4);
        game.setId(234);
        when(gameRepository.findById(234)).thenReturn(Optional.of(game));
        when(seasonRepository.findByGameId(anyInt())).thenReturn(new Season(234,
                null, null, 1.2, 1, 1, 1));
        List<Player> players = new ArrayList<>();
        Player red = new Player(234, 23, "Red");
        red.setId(1);
        players.add(red);
        Player blue = new Player(234, 16, "Blue");
        players.add(blue);
        blue.setId(2);
        Player green = new Player(234, 8, "Green");
        players.add(green);
        green.setId(3);
        List<Territory> redT = new ArrayList<>();
        Territory duke = new Territory(23, 234, "Duke", 10, 10);
        duke.setId(123);
        redT.add(duke);
        when(playerRepository.findByGameId(234)).thenReturn(players);
        when(territoryRepository.findByOwnerId(2)).thenReturn(new ArrayList<>());
        when(territoryRepository.findByOwnerId(3)).thenReturn(new ArrayList<>());
        when(territoryRepository.findByOwnerId(1)).thenReturn(redT);
        Unit unit = new Unit(0, 1, 123);
        when(unitRepository.findByTerritoryIdAndType(anyInt(), anyInt())).thenReturn(unit);
        finishTurnService.finishTurn(234);
        assertEquals(unit.getCount(), 2);
        assertFalse(green.getAlive());
        assertFalse(blue.getAlive());
        assertTrue(red.getAlive());
        assertTrue(game.getGameOver());
        assertEquals(red.getTotalFood(), 50);
        assertEquals(red.getTotalTech(), 52);
    }
}