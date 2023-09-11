package ru.practicum.ewm.service.event.data.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.service.category.data.Category;
import ru.practicum.ewm.service.event.data.location.Location;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventDate", source = "dto.eventTimestamp")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "location", source = "location")
    Event fromDto(EventNewDto dto, Category category, Location location);

    EventFullDto toFullDto(Event entity);

    EventShortDto toShortDto(Event entity);
}