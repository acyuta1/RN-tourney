package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Match;
import com.acyuta.rf.tournament.core.model.OccurrenceStatus;
import com.acyuta.rf.tournament.core.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query(value = "select match.* from tourney.match match where " +
            "match.round_id = :#{#round} and " +
            "(:#{#playerOne} in (match.player_one, match.player_two) " +
            "or :#{#playerTwo} in (match.player_one, match.player_two))", nativeQuery = true)
    List<Match> findDupPlayersOfRound(@Param("round") Long roundId, @Param("playerOne") Long playerOne, @Param("playerTwo") Long playerTwo);


    @Query(value = "select m.* from tourney.match m join tourney.round r on m.round_id = r.id\n" +
            "    join tourney.occurrence o on r.occurrence_id = o.id\n" +
            "    where m.round_id = :#{#round} and r.occurrence_id = :#{#occurrence} and m.id = :#{#match} and m.match_status = 'ONGOING'", nativeQuery = true)
    Optional<Match> checkIfMatchExists(@Param("round") Long roundId, @Param("occurrence") Long occurrenceId, @Param("match") Long matchId);

    List<Match> findAllByRoundIdAndMatchStatus(Long roundId, OccurrenceStatus matchStatus);

    List<Match> findAllByRoundId(Long roundId);
}
