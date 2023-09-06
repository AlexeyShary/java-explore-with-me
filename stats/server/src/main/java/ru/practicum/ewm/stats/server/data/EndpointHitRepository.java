package ru.practicum.ewm.stats.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT e.app AS app, e.uri AS uri, COUNT(DISTINCT e.ip) AS hits " +
            "FROM EndpointHit e " +
            "WHERE e.hitTimestamp BETWEEN :start AND :end " +
            "AND ((:uris) IS NULL OR e.uri IN :uris) " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC")
    List<ViewStatsProjection> findUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT e.app AS app, e.uri AS uri, COUNT(e.ip) AS hits " +
            "FROM EndpointHit e " +
            "WHERE e.hitTimestamp BETWEEN :start AND :end " +
            "AND ((:uris) IS NULL OR e.uri IN :uris) " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC")
    List<ViewStatsProjection> findNotUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris);

}

