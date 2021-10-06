package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Match;
import com.acyuta.rf.tournament.core.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    @Query(value = "select t.* from tourney.match m join tourney.round r on m.round_id = r.id\n" +
            "    join tourney.occurrence o on r.occurrence_id = o.id\n" +
            "    join tourney.tournament t on t.id = o.tournament_id where m.id = :#{#match}", nativeQuery = true)
    Optional<Tournament> findTournamentByMatch(@Param("match") Long matchId);
}
