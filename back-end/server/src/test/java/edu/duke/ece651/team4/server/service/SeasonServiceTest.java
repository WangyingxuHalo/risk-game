package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.Season;
import edu.duke.ece651.team4.server.repository.SeasonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class SeasonServiceTest {
    @MockBean
    SeasonRepository seasonRepository;
    @Autowired
    SeasonService seasonService;

    @Test
    void test_spring() {
        Season spring = seasonService.generateSpring(5);
        assertEquals(spring.getSeason(), "Spring");
        assertEquals(spring.getAttackAdjust(), 1);
        assertEquals(spring.getGameId(), 5);
        assertTrue(spring.getFoodAdjust() >= 0.9);
        assertTrue(spring.getFoodAdjust() <= 2.6);
        assertEquals(spring.getTechAdjust(), 1);
        assertEquals(spring.getMoveAdjust(), 1);
    }

    @Test
    void test_winter() {
        Season winter = seasonService.generateWinter(5);
        assertEquals(winter.getSeason(), "Winter");
        assertEquals(winter.getAttackAdjust(), 0.5);
        assertEquals(winter.getGameId(), 5);
        assertTrue(winter.getFoodAdjust() >= 0.39);
        assertTrue(winter.getFoodAdjust() <= 0.81);
        assertEquals(winter.getTechAdjust(), 1);
        assertEquals(winter.getMoveAdjust(), 1);
    }

    @Test
    void test_fall() {
        Season fall = seasonService.generateFall(5);
        assertEquals(fall.getSeason(), "Fall");
        assertEquals(fall.getFoodAdjust(), 1);
        assertEquals(fall.getGameId(), 5);
        assertTrue(fall.getTechAdjust() >= 1.4);
        assertTrue(fall.getTechAdjust() <= 2.1);
        assertEquals(fall.getAttackAdjust(), 1);
        assertEquals(fall.getMoveAdjust(), 1);
    }

    @Test
    void test_summer() {
        Season summer = seasonService.generateSummer(5);
        assertEquals(summer.getSeason(), "Summer");
        assertEquals(summer.getFoodAdjust(), 1);
        assertEquals(summer.getGameId(), 5);
        assertEquals(summer.getTechAdjust(), 1);
        assertEquals(summer.getAttackAdjust(), 1);
        assertEquals(summer.getMoveAdjust(), 2);
    }

    @Test
    void test_check_and_update_season() {
        when(seasonRepository.deleteByGameId(anyInt())).thenReturn((long) 1);
        seasonService.checkAndUpdateSeason(4, 2);
        verify(seasonRepository, times(0)).save(any());
        verify(seasonRepository, times(0)).deleteByGameId(anyInt());
        seasonService.checkAndUpdateSeason(45, 1);
        verify(seasonRepository, times(1)).save(any());
        verify(seasonRepository, times(1)).deleteByGameId(45);
    }

    @Test
    void test_change_season() {
        when(seasonRepository.deleteByGameId(anyInt())).thenReturn((long) 1);
        seasonService.changeSeason(7, 4);
        verify(seasonRepository, times(1)).save(any());
        verify(seasonRepository, times(1)).deleteByGameId(4);
        seasonService.changeSeason(10, 4);
        verify(seasonRepository, times(2)).save(any());
        verify(seasonRepository, times(2)).deleteByGameId(4);
        seasonService.changeSeason(4, 4);
        verify(seasonRepository, times(3)).save(any());
        verify(seasonRepository, times(3)).deleteByGameId(4);
    }
}