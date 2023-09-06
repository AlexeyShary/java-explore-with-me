package ru.practicum.ewm.service.user.data;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    UserShortDto toShortDto(User user);

    User fromDto(UserDto userDto);
}
