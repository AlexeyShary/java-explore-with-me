package ru.practicum.ewm.service.participationRequest.logic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ParticipationRequestControllerPrivate.class)
class ParticipationRequestControllerPrivateTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipationRequestService participationRequestService;

    @Test
    public void getAll_allValid() throws Exception {
        when(participationRequestService.getAll(anyLong())).thenReturn(null);

        mockMvc.perform(get("/users/{userId}/requests", 0))
                .andExpect(status().is2xxSuccessful());

        verify(participationRequestService, times(1)).getAll(anyLong());
        verifyNoMoreInteractions(participationRequestService);
    }

    @Test
    public void create_allValid() throws Exception {
        when(participationRequestService.create(anyLong(), anyLong())).thenReturn(null);

        mockMvc.perform(post("/users/{userId}/requests", 0)
                        .param("eventId", "0"))
                .andExpect(status().is2xxSuccessful());

        verify(participationRequestService, times(1)).create(anyLong(), anyLong());
        verifyNoMoreInteractions(participationRequestService);
    }

    @Test
    public void patch_allValid() throws Exception {
        when(participationRequestService.patch(anyLong(), anyLong())).thenReturn(null);

        mockMvc.perform(patch("/users/{userId}/requests/{requestId}/cancel", 0, 0))
                .andExpect(status().is2xxSuccessful());

        verify(participationRequestService, times(1)).patch(anyLong(), anyLong());
        verifyNoMoreInteractions(participationRequestService);
    }
}