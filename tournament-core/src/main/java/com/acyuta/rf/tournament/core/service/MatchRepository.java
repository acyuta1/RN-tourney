package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Match;
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
    List<Match> findDupPlayersOfRound(@Param("round") Long roundId, @Param("playerOne") String playerOne, @Param("playerTwo") String playerTwo);
}
