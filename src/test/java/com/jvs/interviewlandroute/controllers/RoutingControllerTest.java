package com.jvs.interviewlandroute.controllers;

import com.jvs.interviewlandroute.dtos.RouteDto;
import com.jvs.interviewlandroute.exceptions.NoPathException;
import com.jvs.interviewlandroute.services.RoutingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoutingController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoutingControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private RoutingController routingController;

    @MockBean
    private RoutingService routingService;

    @BeforeEach
    private void beforeEach() {
        Mockito.clearInvocations(routingService);
    }

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new RestResponseExceptionHandler(), routingController).build();
    }

    @Test
    void testPathFound() throws Exception {
        Mockito.when(routingService.route(anyString(), anyString())).thenReturn(new RouteDto(List.of("eu1", "eu2")));

        mockMvc.perform(MockMvcRequestBuilders.get("/routing/eu1/eu2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.route").isArray());
    }

    @Test
    void testNoPathFound() throws Exception {
        Mockito.when(routingService.route(anyString(), anyString())).thenThrow(new NoPathException("No path found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/routing/eu1/eu2"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("No path found"));
    }

    @Test
    void testValidationException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/routing/e1/e2"))
                .andExpect(status().isBadRequest());
    }


}