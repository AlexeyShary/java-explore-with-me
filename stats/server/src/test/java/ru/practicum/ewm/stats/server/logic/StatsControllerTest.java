package ru.practicum.ewm.stats.server.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.stats.dto.EndpointHitDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
class StatsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatsService statsService;

    @Test
    void getStats_allValid() throws Exception {
        String start = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String end = LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String uris = "Uri1, Uri2";
        String unique = "false";

        when(statsService.getStats(any(), any(), any(), any())).thenReturn(null);

        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("uris", uris)
                        .param("unique", unique))
                .andExpect(status().is2xxSuccessful());

        verify(statsService, times(1)).getStats(any(), any(), any(), any());
        verifyNoMoreInteractions(statsService);
    }

    @Test
    void getStats_onlyRequiredValid() throws Exception {
        String start = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String end = LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        when(statsService.getStats(any(), any(), any(), any())).thenReturn(null);

        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end))
                .andExpect(status().is2xxSuccessful());

        verify(statsService, times(1)).getStats(any(), any(), any(), any());
        verifyNoMoreInteractions(statsService);
    }

    @Test
    void getStats_noRequiredParam() throws Exception {
        String start = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        mockMvc.perform(get("/stats")
                        .param("start", start))
                .andExpect(status().is4xxClientError());

        verifyNoMoreInteractions(statsService);
    }

    @Test
    void createEndpointHit_allValid() throws Exception {
        EndpointHitDto endpointHitDto = getValidEndpointHitDto();

        doNothing().when(statsService).createEndpointHit(any());

        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(endpointHitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(statsService, times(1)).createEndpointHit(any());
        verifyNoMoreInteractions(statsService);
    }

    @Test
    void createEndpointHit_blankApp() throws Exception {
        EndpointHitDto endpointHitDto = getValidEndpointHitDto();
        endpointHitDto.setApp("   ");

        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(endpointHitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verifyNoMoreInteractions(statsService);
    }

    @Test
    void createEndpointHit_blankUri() throws Exception {
        EndpointHitDto endpointHitDto = getValidEndpointHitDto();
        endpointHitDto.setUri("   ");

        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(endpointHitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verifyNoMoreInteractions(statsService);
    }

    @Test
    void createEndpointHit_blankIp() throws Exception {
        EndpointHitDto endpointHitDto = getValidEndpointHitDto();
        endpointHitDto.setIp("   ");

        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(endpointHitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verifyNoMoreInteractions(statsService);
    }

    @Test
    void createEndpointHit_nullTimestamp() throws Exception {
        EndpointHitDto endpointHitDto = getValidEndpointHitDto();
        endpointHitDto.setHitTimestamp(null);

        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(endpointHitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verifyNoMoreInteractions(statsService);
    }

    private EndpointHitDto getValidEndpointHitDto() {
        return EndpointHitDto.builder()
                .app("TestApp")
                .uri("TestUri")
                .ip("0.0.0.0")
                .hitTimestamp(LocalDateTime.now())
                .build();
    }
}