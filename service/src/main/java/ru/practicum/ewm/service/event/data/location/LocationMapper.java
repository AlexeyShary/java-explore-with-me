package ru.practicum.ewm.service.event.data.location;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDto toDto(Location entity);

    Location fromDto(LocationDto dto);
}
