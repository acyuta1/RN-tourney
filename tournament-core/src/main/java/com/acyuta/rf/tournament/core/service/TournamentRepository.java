package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
