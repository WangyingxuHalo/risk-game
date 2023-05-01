package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.model.TwentySidedDice;
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
class AttackServiceTest {
    @MockBean
    TwentySidedDice dice;

    @MockBean
    ResolveRepository resolveRepository;

    @MockBean
    ResolveDetailRepository resolveDetailRepository;

    @Autowired
    AttackService attackService;

    @MockBean
    TerritoryRepository territoryRepository;

    @MockBean
    GameRepository gameRepository;

    @MockBean
    PlayerOrderRepository playerOrderRepository;

    @MockBean
    UnitRepository unitRepository;

    @MockBean
    AttackRepository attackRepository;

    @MockBean
    FinishTurnService finishTurnService;

    @Test
    void conductAttack() {
        when(dice.roll()).thenReturn(10);
        Game game = new Game(3);
        game.setId(234);
        when(gameRepository.findById(234)).thenReturn(Optional.of(game));
        PlayerOrder redOrder = new PlayerOrder(0, 1, false, 234);
        redOrder.setId(1);
        PlayerOrder blueOrder = new PlayerOrder(0, 2, false, 234);
        blueOrder.setId(2);
        PlayerOrder greenOrder = new PlayerOrder(0, 3, true, 234);
        greenOrder.setId(3);
        List<PlayerOrder> orders = new ArrayList<>();
        orders.add(redOrder);
        orders.add(blueOrder);
        orders.add(greenOrder);
        when(playerOrderRepository.findByGameID(234)).thenReturn(orders);
        List<Attack> redAttacks = new ArrayList<>();
        redAttacks.add(new Attack(1, 3, 1, 0, 1));
        redAttacks.add(new Attack(1, 3, 1, 0, 1));
        redAttacks.add(new Attack(1, 3, 1, 1, 1));
        redAttacks.add(new Attack(1, 3, 1, 1, 1));
        redAttacks.add(new Attack(2, 3, 1, 0, 1));
        redAttacks.add(new Attack(2, 3, 1, 1, 1));
        redAttacks.add(new Attack(2, 5, 1, 1, 1));
        when(attackRepository.findByOrderId(1)).thenReturn(redAttacks);
        List<Attack> blueAttacks = new ArrayList<>();
        blueAttacks.add(new Attack(4, 3, 2, 2, 2));
        blueAttacks.add(new Attack(4, 5, 2, 0, 2));
        blueAttacks.add(new Attack(4, 5, 2, 0, 2));
        blueAttacks.add(new Attack(4, 5, 2, 3, 2));
        when(attackRepository.findByOrderId(2)).thenReturn(blueAttacks);
        List<Attack> greenAttacks = new ArrayList<>();
        when(attackRepository.findByOrderId(3)).thenReturn(greenAttacks);
        List<Unit> defense3 = new ArrayList<>();
        defense3.add(new Unit(0, 2, 3));
        defense3.add(new Unit(1, 2, 3));
        List<Unit> defense5 = new ArrayList<>();
        defense5.add(new Unit(0, 1, 5));
        defense5.add(new Unit(1, 0, 5));
        defense5.add(new Unit(3, 3, 5));
        when(unitRepository.findByTerritoryId(3)).thenReturn(defense3);
        when(unitRepository.findByTerritoryId(5)).thenReturn(defense5);
        Territory t3 = new Territory(2, 234, "Duke", 0, 0);
        t3.setId(3);
        Territory t5 = new Territory(3, 234, "UNC", 0, 0);
        t5.setId(5);
        when(territoryRepository.findById(3)).thenReturn(Optional.of(t3));
        when(territoryRepository.findById(5)).thenReturn(Optional.of(t5));
        Unit u = new Unit(0, 0, 0);
        when(unitRepository.findByTerritoryIdAndType(anyInt(), anyInt())).thenReturn(u);
        Resolve resolve = new Resolve(34, 23, 7, 234);
        List<Resolve> resolves = new ArrayList<>();
        resolves.add(resolve);
        resolve.setId(4);
        when(resolveRepository.findByGameId(234)).thenReturn(resolves);
        when(resolveRepository.save(any())).thenReturn(resolve);
        when(resolveRepository.findByAttackedTerritoryId(anyInt())).thenReturn(Optional.of(resolve));
        List<ResolveDetail> details = new ArrayList<>();
        ResolveDetail detail = new ResolveDetail(1, 2, 3);
        details.add(detail);
        when(resolveDetailRepository.findByResolveId(anyInt())).thenReturn(details);
        attackService.conductAttack(234);
    }

    @Test
    public void test_not_ready() {
        Game game = new Game(3);
        game.setId(234);
        when(gameRepository.findById(234)).thenReturn(Optional.of(game));
        PlayerOrder redOrder = new PlayerOrder(0, 1, false, 234);
        redOrder.setId(1);
        PlayerOrder blueOrder = new PlayerOrder(0, 2, false, 234);
        blueOrder.setId(2);
        List<PlayerOrder> orders = new ArrayList<>();
        orders.add(redOrder);
        orders.add(blueOrder);
        boolean ans = attackService.conductAttack(234);
        assertFalse(ans);
    }
}