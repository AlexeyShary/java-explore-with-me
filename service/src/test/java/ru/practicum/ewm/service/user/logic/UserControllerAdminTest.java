package ru.practicum.ewm.service.user.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.service.user.data.UserDto;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserControllerAdmin.class)
class UserControllerAdminTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void get_allValid() throws Exception {
        when(userService.get(any(), anyInt(), anyInt())).thenReturn(null);

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().is2xxSuccessful());

        verify(userService, times(1)).get(any(), anyInt(), anyInt());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void create_allValid() throws Exception {
        when(userService.create(any())).thenReturn(null);

        mockMvc.perform(post("/admin/users")
                        .content(objectMapper.writeValueAsString(getValidUserDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        verify(userService, times(1)).create(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void delete_allValid() throws Exception {
        doNothing().when(userService).delete(anyLong());

        mockMvc.perform(delete("/admin/users/{userId}", 0))
                .andExpect(status().is2xxSuccessful());

        verify(userService, times(1)).delete(anyLong());
        verifyNoMoreInteractions(userService);
    }

    private UserDto getValidUserDto() {
        return UserDto.builder()
                .email("valid@mail.com")
                .name("Valid")
                .build();
    }
}