package ru.practicum.ewm.service.category.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.service.category.data.CategoryDto;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryControllerAdmin.class)
class CategoryControllerAdminTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void create_allValid() throws Exception {
        when(categoryService.create(any())).thenReturn(null);

        mockMvc.perform(post("/admin/categories")
                        .content(objectMapper.writeValueAsString(getValidCategoryDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).create(any());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void patch_allValid() throws Exception {
        when(categoryService.patch(anyLong(), any())).thenReturn(null);

        mockMvc.perform(patch("/admin/categories/{catId}", 0)
                        .content(objectMapper.writeValueAsString(getValidCategoryDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).patch(anyLong(), any());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void delete_allValid() throws Exception {
        doNothing().when(categoryService).delete(anyLong());

        mockMvc.perform(delete("/admin/categories/{catId}", 0))
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).delete(anyLong());
        verifyNoMoreInteractions(categoryService);
    }

    private CategoryDto getValidCategoryDto() {
        return CategoryDto.builder()
                .name("Test category")
                .build();
    }
}