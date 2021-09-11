package com.acyuta.rf.tournament.service;

import com.acyuta.rf.tournament.model.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {
}
