package ru.practicum.ewm.stats.server.data;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class StatsMapper {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHitDto toDto(EndpointHit entity) {
        return EndpointHitDto.builder()
                .app(entity.getApp())
                .uri(entity.getUri())
                .ip(entity.getIp())
                .timestamp(entity.getTimestamp().format(DATE_TIME_FORMATTER))
                .build();
    }

    public EndpointHit fromDto(EndpointHitDto dto) {
        EndpointHit entity = new EndpointHit();
        entity.setApp(dto.getApp());
        entity.setUri(dto.getUri());
        entity.setIp(dto.getIp());
        entity.setTimestamp(LocalDateTime.parse(dto.getTimestamp(), DATE_TIME_FORMATTER));
        return entity;
    }
}