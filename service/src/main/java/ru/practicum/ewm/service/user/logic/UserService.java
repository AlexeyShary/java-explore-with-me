package ru.practicum.ewm.service.user.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.user.data.User;
import ru.practicum.ewm.service.user.data.UserDto;
import ru.practicum.ewm.service.user.data.UserMapper;
import ru.practicum.ewm.service.user.data.UserRepository;
import ru.practicum.ewm.service.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDto> get(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<User> page;

        if (ids != null && !ids.isEmpty()) {
            page = userRepository.findAllByIdIn(ids, pageable);
        } else {
            page = userRepository.findAll(pageable);
        }

        return page.getContent().stream()
                .map(UserMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        return UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.fromDto(userDto)));
    }

    @Transactional
    public void delete(long userId) {
        findById(userId);
        userRepository.deleteById(userId);
    }

    private User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " was not found"));
    }
}
