package edu.duke.ece651.team4.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.team4.server.model.UserPlacement;
import edu.duke.ece651.team4.server.service.PlacementService;
import edu.duke.ece651.team4.server.service.ViewService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

class PlacementControllerTest {

    @Autowired
    ViewService viewService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlacementService placementService;

    @Test
    void doUnitsPlacement() throws Exception {
        doNothing().when(placementService).doUnitsPlacement(any());

        UserPlacement userPlacement = new UserPlacement();

        mockMvc.perform(post("/placement")
                        .content(new ObjectMapper().writeValueAsString(userPlacement))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(placementService, times(1)).doUnitsPlacement(any());
    }

    @Test
    void isPlacementDone() throws Exception {
        when(placementService.isPlacementDone(1)).thenReturn(true);

        mockMvc.perform(get("/isPlacementDone/1"))
                .andDo(print())
                .andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(placementService, times(1)).isPlacementDone(1);
    }

}