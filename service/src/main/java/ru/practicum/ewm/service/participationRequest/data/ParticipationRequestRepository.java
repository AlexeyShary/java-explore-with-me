package ru.practicum.ewm.service.participationRequest.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequesterId(Long id);

    List<ParticipationRequest> findAllByEventId(Long id);

    List<ParticipationRequest> findAllByIdIn(List<Long> ids);

    @Query("SELECT COUNT(pr) FROM ParticipationRequest pr WHERE pr.event.id = :eventId AND pr.status = :status")
    Long countByEventIdAndStatus(@Param("eventId") Long eventId,
                                 @Param("status") ParticipationRequestState status);
}
