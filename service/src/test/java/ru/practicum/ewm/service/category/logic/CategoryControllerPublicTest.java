package ru.practicum.ewm.service.category.logic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryControllerPublic.class)
class CategoryControllerPublicTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void getAll_allValid() throws Exception {
        when(categoryService.getAll(anyInt(), anyInt())).thenReturn(null);

        mockMvc.perform(get("/categories"))
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).getAll(anyInt(), anyInt());
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getById_allValid() throws Exception {
        when(categoryService.getById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/categories/{catId}", 0))
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).getById(anyLong());
        verifyNoMoreInteractions(categoryService);
    }
}