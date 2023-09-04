package ru.practicum.ewm.stats.server.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.server.data.EndpointHitRepository;
import ru.practicum.ewm.stats.server.data.StatsMapper;
import ru.practicum.ewm.stats.server.data.ViewStatsProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final EndpointHitRepository endpointHitRepository;

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<ViewStatsProjection> results;

        if (unique) {
            results = endpointHitRepository.findUniqueStats(start, end, uris);
        } else {
            results = endpointHitRepository.findNotUniqueStats(start, end, uris);
        }

        return results.stream()
                .map(result -> ViewStatsDto.builder()
                        .app(result.getApp())
                        .uri(result.getUri())
                        .hits(result.getHits())
                        .build())
                .collect(Collectors.toList());
    }

    public void createEndpointHit(EndpointHitDto endpointHitDto) {
        endpointHitRepository.save(StatsMapper.fromDto(endpointHitDto));
    }
}