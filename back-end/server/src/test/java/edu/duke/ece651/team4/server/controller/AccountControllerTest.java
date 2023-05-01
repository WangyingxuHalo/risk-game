package edu.duke.ece651.team4.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.team4.server.entity.GameUser;
import edu.duke.ece651.team4.server.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void loginPassesWithStatusOK() throws Exception {
        GameUser gameUser = new GameUser("user", "pass");
        when(accountService.attemptLogin(any())).thenReturn(1);

        mockMvc.perform(post("/login", gameUser)
                        .content(new ObjectMapper().writeValueAsString(gameUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("userId").exists());

        verify(accountService, times(1)).attemptLogin(any());
    }

    @Test
    void loginFailsWithStatusNotFound() throws Exception {
        GameUser gameUser = new GameUser("user", "pass");
        when(accountService.attemptLogin(any())).thenThrow(new IllegalArgumentException("not found"));

        mockMvc.perform(post("/login", gameUser)
                        .content(new ObjectMapper().writeValueAsString(gameUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("not found")));

        verify(accountService, times(1)).attemptLogin(any());
    }

    @Test
    void registerPassesWithStatusOK() throws Exception {
        GameUser gameUser = new GameUser("user", "pass");
        when(accountService.attemptRegistration(any())).thenReturn(1);

        mockMvc.perform(post("/register", gameUser)
                        .content(new ObjectMapper().writeValueAsString(gameUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("userId").exists());

        verify(accountService, times(1)).attemptRegistration(any());
    }

    @Test
    void registerFailsWithStatusConflict() throws Exception {
        GameUser gameUser = new GameUser("user", "pass");
        when(accountService.attemptRegistration(any())).thenThrow(new IllegalArgumentException("not found"));

        mockMvc.perform(post("/register", gameUser)
                        .content(new ObjectMapper().writeValueAsString(gameUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("not found")));

        verify(accountService, times(1)).attemptRegistration(any());
    }
}