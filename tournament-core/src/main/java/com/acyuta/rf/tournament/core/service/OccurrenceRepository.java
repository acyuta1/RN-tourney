package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    // 1. find between startdate in current year

    Boolean existsByTournamentIdAndOccurrenceDateBetween(Long tournamentId, LocalDate begin, LocalDate end);

    Optional<Occurrence> findByTournamentIdAndOccurrenceDateBetween(Long tournamentId, LocalDate begin, LocalDate end);
}
