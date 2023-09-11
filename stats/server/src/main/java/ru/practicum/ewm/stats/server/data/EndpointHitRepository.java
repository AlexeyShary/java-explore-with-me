package ru.practicum.ewm.stats.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT e.app AS app, e.uri AS uri, COUNT(DISTINCT e.ip) AS hits " +
            "FROM EndpointHit e " +
            "WHERE e.hitTimestamp BETWEEN :start AND :end " +
            "AND ((:uris) IS NULL OR e.uri IN :uris) " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC")
    List<ViewStatsProjection> findUniqueStats(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end,
                                              @Param("uris") List<String> uris);

    @Query("SELECT e.app AS app, e.uri AS uri, COUNT(e.ip) AS hits " +
            "FROM EndpointHit e " +
            "WHERE e.hitTimestamp BETWEEN :start AND :end " +
            "AND ((:uris) IS NULL OR e.uri IN :uris) " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY hits DESC")
    List<ViewStatsProjection> findNotUniqueStats(@Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end,
                                                 @Param("uris") List<String> uris);

}

