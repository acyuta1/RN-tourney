package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.mappers.MatchMapper;
import com.acyuta.rf.tournament.core.model.Match;
import com.acyuta.rf.tournament.core.model.OccurrenceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    private final RoundService roundService;

    public Match findMatch(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "match.not.found"));
    }

    public MatchDto addMatch(Long roundId, MatchDto matchDto) {

        // 1. round exists
        var round = roundService.findRound(roundId);

        // 2. round status ongoing or not
        if (!OccurrenceStatus.ONGOING.equals(round.getRoundStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "round is not open for new match enrollment!!!!");
        }

        if(matchDto.getPlayerOne().equals(matchDto.getPlayerTwo())) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "players cannot be the same");
        }
        // 3. duplicate entry of player name/combo
        var duplicates = matchRepository.findDupPlayersOfRound(roundId, matchDto.getPlayerOne(), matchDto.getPlayerTwo());

        if (duplicates.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "duplicate players");
        }
        return matchMapper.toDto(matchRepository.save(matchMapper.fromDto(matchDto)));
    }
}
