package ru.practicum.ewm.service.compilation.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.service.compilation.data.CompilationNewDto;
import ru.practicum.ewm.service.compilation.data.CompilationUpdateRequest;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CompilationControllerAdmin.class)
class CompilationControllerAdminTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompilationService compilationService;

    @Test
    public void create_allValid() throws Exception {
        when(compilationService.create(any())).thenReturn(null);

        mockMvc.perform(post("/admin/compilations")
                        .content(objectMapper.writeValueAsString(getValidCompilationNewDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(compilationService, times(1)).create(any());
        verifyNoMoreInteractions(compilationService);
    }

    @Test
    public void patch_allValid() throws Exception {
        when(compilationService.patch(anyLong(), any())).thenReturn(null);

        mockMvc.perform(patch("/admin/compilations/{compId}", 0)
                        .content(objectMapper.writeValueAsString(CompilationUpdateRequest.builder().build()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(compilationService, times(1)).patch(anyLong(), any());
        verifyNoMoreInteractions(compilationService);
    }

    @Test
    public void delete_allValid() throws Exception {
        doNothing().when(compilationService).delete(anyLong());

        mockMvc.perform(delete("/admin/compilations/{compId}", 0))
                .andExpect(status().is2xxSuccessful());

        verify(compilationService, times(1)).delete(anyLong());
        verifyNoMoreInteractions(compilationService);
    }

    private CompilationNewDto getValidCompilationNewDto() {
        return CompilationNewDto.builder()
                .title("Test title")
                .build();
    }
}