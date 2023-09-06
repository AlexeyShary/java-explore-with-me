package ru.practicum.ewm.stats.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public class StatsClient {
    private final WebClient webClient;

    public StatsClient(String serverUrl) {
        webClient = WebClient.builder().baseUrl(serverUrl).build();
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri("/stats?start={start}&end={end}&uris={uris}&unique={unique}", start, end, uris, unique)
                .retrieve()
                .bodyToFlux(ViewStatsDto.class)
                .collectList()
                .block();
    }

    public void createEndpointHit(EndpointHitDto endpointHitDto) {
        webClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(endpointHitDto))
                .exchange()
                .block();
    }
}