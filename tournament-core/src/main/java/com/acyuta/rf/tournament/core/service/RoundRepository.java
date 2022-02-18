package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.OccurrenceStatus;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.model.RoundType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

    @Query(value = "select round.* from tourney.round round where round.occurrence_id = :#{#occurrenceId} " +
            "and round.round_status = :#{#status}", nativeQuery = true)
    List<Round> findPreviousEntry(@Param("occurrenceId") Long occurrenceId,
                                  @Param("status") OccurrenceStatus status);

    List<Round> findAllByOccurrenceIdAndRoundStatus(Long occurrenceId, OccurrenceStatus status);

    Optional<Round> findByOccurrenceIdAndRoundType(Long occurrenceId, RoundType roundType);

    List<Round> findAllByOccurrenceId(Long occurrenceId);
}
