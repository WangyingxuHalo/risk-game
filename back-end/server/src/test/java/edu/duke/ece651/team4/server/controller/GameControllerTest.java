package edu.duke.ece651.team4.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.team4.server.entity.GameUser;
import edu.duke.ece651.team4.server.model.*;
import edu.duke.ece651.team4.server.service.ActionService;
import edu.duke.ece651.team4.server.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private ActionService actionService;

    @Test
    void createGame() throws Exception {
        GameUser gameUser = new GameUser("user", "pass");
        ResGame resGame = new ResGame(1, 1, "Red");
        when(gameService.createGame(1, 4)).thenReturn(resGame);

        mockMvc.perform(post("/createGame/1/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("gameID").exists())
                .andExpect(jsonPath("playerID").exists())
                .andExpect(jsonPath("color").exists());

        verify(gameService, times(1)).createGame(1, 4);
    }

    @Test
    void joinGame() throws Exception {
        ResJoinGame resJoinGame = new ResJoinGame();
        resJoinGame.setPlayerID(1);
        when(gameService.joinGame(1, 1)).thenReturn(resJoinGame);

        mockMvc.perform(post("/joinGame/1/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(gameService, times(1)).joinGame(1, 1);
    }

    @Test
    void resumeGame() throws Exception {
        when(gameService.resumeGame(1, 1)).thenReturn(true);
        when(gameService.resumeGame(2, 1)).thenReturn(false);

        mockMvc.perform(post("/resumeGame/1/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(gameService, times(1)).resumeGame(1, 1);

        mockMvc.perform(post("/resumeGame/2/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(gameService, times(1)).resumeGame(2, 1);
    }

    @Test
    void getGameMap() throws Exception {
        ResGameMap resGameMap = new ResGameMap();
        when(gameService.getGameMap(1)).thenReturn(resGameMap);

        mockMvc.perform(get("/gameMap/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(gameService, times(1)).getGameMap(1);
    }

    @Test
    void getGames() throws Exception {
        ResGames games = new ResGames();
        when(gameService.getGames(1)).thenReturn(games);

        mockMvc.perform(get("/games/1"))
                .andDo(print())
                .andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(gameService, times(1)).getGames(1);
    }

    @Test
    void isGameReady() throws Exception {
        when(gameService.isGameReady(1)).thenReturn(true);

        mockMvc.perform(get("/isGameReady/1"))
                .andDo(print())
                .andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(gameService, times(1)).isGameReady(1);
    }

    @Test
    void conductTurn() throws Exception {
        OnePlayerTurn onePlayerTurn = new OnePlayerTurn();
        when(actionService.conductTurn(any())).thenReturn("turn complete");

        mockMvc.perform(post("/done")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(onePlayerTurn)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", is("turn complete")));

        verify(actionService, times(1)).conductTurn(any());
    }

    @Test
    void getTurn() throws Exception {
        ResGetTurn resGetTurn = new ResGetTurn();
        when(gameService.getTurn(1)).thenReturn(resGetTurn);

        mockMvc.perform(get("/getTurn/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(gameService, times(1)).getTurn(1);
    }

    @Test
    void getAllInfo() throws Exception {
        ResAllInfo resAllInfo = new ResAllInfo();
        when(gameService.getAllInfo(1)).thenReturn(resAllInfo);

        mockMvc.perform(get("/allInfo/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(gameService, times(1)).getAllInfo(1);
    }

    @Test
    void playBonus() throws Exception {
        Bonus bonus = new Bonus("type", "territory", 3);
        when(gameService.playBonus(any())).thenReturn(0);

        mockMvc.perform(post("/bonus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bonus)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", is(0)));

        verify(gameService, times(1)).playBonus(any());
    }

}