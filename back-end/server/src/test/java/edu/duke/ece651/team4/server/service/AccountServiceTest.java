package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.GameUser;
import edu.duke.ece651.team4.server.repository.GameUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @MockBean
    GameUserRepository gameUserRepository;

    @Test
    void attemptLogin() {
        GameUser gameUserOnDB = new GameUser("testUser", "pass");
        gameUserOnDB.setId(1);
        when(gameUserRepository.findByUsername("testUser")).thenReturn(gameUserOnDB);

        assertEquals(1, accountService.attemptLogin(gameUserOnDB));
    }

    @Test
    void attemptLoginThrowsException() {
        GameUser gameUserOnDB = new GameUser("testUser", "pass");
        gameUserOnDB.setId(1);
        when(gameUserRepository.findByUsername("testUser")).thenReturn(gameUserOnDB);

        GameUser gameUser = new GameUser("testUser", "differentPass");
        assertThrows(IllegalArgumentException.class, () -> accountService.attemptLogin(gameUser));

        GameUser newGameUser = new GameUser("newUser", "pass");
        assertThrows(IllegalArgumentException.class, () -> accountService.attemptLogin(newGameUser));
    }

    @Test
    void attemptRegistration() {
        when(gameUserRepository.findByUsername("testUser")).thenReturn(null);
        GameUser gameUserOnDB = new GameUser("testUser", "pass");
        gameUserOnDB.setId(1);
        when(gameUserRepository.save(any())).thenReturn(gameUserOnDB);

        assertEquals(1, accountService.attemptRegistration(gameUserOnDB));
    }

    @Test
    void attemptRegistrationThrowsException() {
        GameUser gameUserOnDB = new GameUser("testUser", "pass");
        gameUserOnDB.setId(1);
        when(gameUserRepository.findByUsername("testUser")).thenReturn(gameUserOnDB);

        GameUser gameUser = new GameUser("testUser", "pass");
        assertThrows(IllegalArgumentException.class, () -> accountService.attemptRegistration(gameUser));
    }
}