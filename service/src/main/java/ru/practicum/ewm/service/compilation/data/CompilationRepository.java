package ru.practicum.ewm.service.compilation.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query("SELECT c FROM Compilation c " +
            "WHERE ((:pinned) IS NULL OR c.pinned = :pinned)")
    Page<Compilation> findAllByPublic(@Param("pinned") Boolean pinned,
                                      Pageable pageable);
}
