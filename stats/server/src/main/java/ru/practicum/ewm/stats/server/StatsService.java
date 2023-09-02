package ru.practicum.ewm.stats.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final EndpointHitRepository endpointHitRepository;

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<Object[]> results;

        if (unique) {
            results = endpointHitRepository.findUniqueStats(start, end, uris);
        } else {
            results = endpointHitRepository.findNotUniqueStats(start, end, uris);
        }

        return results.stream()
                .map(result -> ViewStatsDto.builder()
                        .app((String) result[0])
                        .uri((String) result[1])
                        .hits((Long) result[2])
                        .build())
                .collect(Collectors.toList());
    }

    public void createEndpointHit(EndpointHitDto endpointHitDto) {
        endpointHitRepository.save(StatsMapper.fromDto(endpointHitDto));
    }
}