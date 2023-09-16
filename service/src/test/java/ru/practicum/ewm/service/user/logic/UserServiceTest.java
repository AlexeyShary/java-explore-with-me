package ru.practicum.ewm.service.user.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.service.user.data.User;
import ru.practicum.ewm.service.user.data.UserDto;
import ru.practicum.ewm.service.user.data.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void get_emptyListTest() {
        List<Long> ids = new ArrayList<>();

        when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));

        userService.get(ids, 0, 10);

        verify(userRepository, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void get_notEmptyListTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        when(userRepository.findAllByIdIn(eq(ids), any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));

        userService.get(ids, 0, 10);

        verify(userRepository, times(1)).findAllByIdIn(eq(ids), any(Pageable.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createTest() {
        UserDto userDto = UserDto.builder().build();

        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.create(userDto);

        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteTest() {
        long userId = 1L;

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(new User()));
        doNothing().when(userRepository).deleteById(eq(userId));

        userService.delete(userId);

        verify(userRepository, times(1)).findById(eq(userId));
        verify(userRepository, times(1)).deleteById(eq(userId));
        verifyNoMoreInteractions(userRepository);
    }
}