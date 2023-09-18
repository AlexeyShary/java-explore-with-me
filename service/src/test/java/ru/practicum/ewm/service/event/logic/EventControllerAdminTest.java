package ru.practicum.ewm.service.event.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.service.event.data.event.EventUpdateAdminRequest;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventControllerAdmin.class)
class EventControllerAdminTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    @Test
    public void get_allValid() throws Exception {
        when(eventService.getAllByAdmin(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(null);

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().is2xxSuccessful());

        verify(eventService, times(1)).getAllByAdmin(any(), any(), any(), any(), any(), anyInt(), anyInt());
        verifyNoMoreInteractions(eventService);
    }

    @Test
    public void patch_allValid() throws Exception {
        when(eventService.patchByAdmin(anyLong(), any())).thenReturn(null);

        mockMvc.perform(patch("/admin/events/{eventId}", 0)
                        .content(objectMapper.writeValueAsString(EventUpdateAdminRequest.builder().build()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(eventService, times(1)).patchByAdmin(anyLong(), any());
        verifyNoMoreInteractions(eventService);
    }
}