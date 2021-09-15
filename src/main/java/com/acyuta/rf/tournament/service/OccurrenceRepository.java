package com.acyuta.rf.tournament.service;

import com.acyuta.rf.tournament.model.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    // 1. find between startdate in current year

    Boolean existsByOccurrenceDateBetween(LocalDate begin, LocalDate end);
}
