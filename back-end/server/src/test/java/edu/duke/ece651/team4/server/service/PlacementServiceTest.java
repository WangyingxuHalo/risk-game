package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.Player;
import edu.duke.ece651.team4.server.entity.Territory;
import edu.duke.ece651.team4.server.model.Placement;
import edu.duke.ece651.team4.server.model.UserPlacement;
import edu.duke.ece651.team4.server.repository.PlayerRepository;
import edu.duke.ece651.team4.server.repository.TerritoryRepository;
import edu.duke.ece651.team4.server.repository.UnitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlacementServiceTest {
    @Autowired
    PlacementService placementService;

    @MockBean
    TerritoryRepository territoryRepository;

    @MockBean
    UnitRepository unitRepository;

    @MockBean
    PlayerRepository playerRepository;

    @Test
    void test_doPlacement() {
        Placement p1 = new Placement("Duke", 10);
        Placement p2 = new Placement("UNC", 2);
        Placement p3 = new Placement("Kentucky", 14);
        ArrayList<Placement> placements = new ArrayList<>();
        placements.add(p1);
        placements.add(p2);
        placements.add(p3);
        UserPlacement userPlacement = new UserPlacement(43, placements);
        Territory duke = new Territory(43, 0, "Duke", 0, 0);
        duke.setId(1);
        when(territoryRepository.findByNameAndOwnerId(any(), anyInt())).thenReturn(duke);
        when(playerRepository.findById(43)).thenReturn(Optional.of(new Player()));
        placementService.doUnitsPlacement(userPlacement);
        verify(unitRepository, times(21)).save(any());
        verify(playerRepository, times(1)).save(any());
        verify(playerRepository, times(1)).findById(any());
        verify(territoryRepository, times(3)).findByNameAndOwnerId(any(), anyInt());
    }

    @Test
    void testIsPlacementDone() {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player();
        player1.setPlacementDone(true);
        Player player2 = new Player();
        player2.setPlacementDone(false);
        players.add(player1);
        players.add(player2);
        when(playerRepository.findByGameId(1)).thenReturn(players);
        assertFalse(placementService.isPlacementDone(1));
    }

}