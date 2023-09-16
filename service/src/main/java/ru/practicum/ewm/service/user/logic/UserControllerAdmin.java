package ru.practicum.ewm.service.user.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.user.data.UserDto;
import ru.practicum.ewm.service.util.exception.BadRequestException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserControllerAdmin {
    private final UserService userService;

    @GetMapping()
    public List<UserDto> get(@RequestParam(required = false) List<Long> ids,
                             @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                             @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return userService.get(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        String[] parts = userDto.getEmail().split("@");
        String login = parts[0];
        String domain = parts[1];

        String[] domainParts = domain.split("\\.");

        if (login.length() > 64) {
            throw new BadRequestException("Login is too long");
        }

        for (String domainPart : domainParts) {
            if (domainPart.length() > 64) {
                throw new BadRequestException("Domain part is too long");
            }
        }

        return userService.create(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long userId) {
        userService.delete(userId);
    }
}