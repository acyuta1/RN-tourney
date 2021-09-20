package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    // 1. find between startdate in current year

    Boolean existsByOccurrenceDateBetween(LocalDate begin, LocalDate end);
}
