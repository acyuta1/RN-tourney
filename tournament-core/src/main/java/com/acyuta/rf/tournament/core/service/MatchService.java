package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.tournament.core.mappers.MatchMapper;
import com.acyuta.rf.tournament.core.model.Match;
import com.acyuta.rf.tournament.core.model.OccurrenceStatus;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.model.Tournament;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    private final RoundService roundService;

    private final TournamentRepository tournamentRepository;

    private final PlayerService playerService;

    public Match findMatch(Long id) {
        return matchRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "match.not.found"));
    }

    public MatchDto addMatch(Long roundId, MatchDto matchDto) {

        // 1. round exists
        var round = roundService.findRound(roundId);
        var playerOne = playerService.findPlayer(matchDto.getPlayerOne());
        var playerTwo = playerService.findPlayer(matchDto.getPlayerTwo());

        // 2. round status ongoing or not
        if (!OccurrenceStatus.ONGOING.equals(round.getRoundStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "round is not open for new match enrollment!!!!");
        }

        if (matchDto.getPlayerOne().equals(matchDto.getPlayerTwo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "players cannot be the same");
        }
        // 3. duplicate entry of player name/combo
        var duplicates = matchRepository.findDupPlayersOfRound(roundId, playerOne.getId(), playerTwo.getId());

        if (duplicates.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "duplicate players");
        }
        return matchMapper.toDto(matchRepository.save(matchMapper.fromDto(matchDto)));
    }

    public MatchDto checkMatchExistsAndOngoing(Long occurrenceId, Long roundId, Long matchId) {
        return matchMapper.toDto(matchRepository.checkIfMatchExists(roundId, occurrenceId, matchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "match not present, or active currently!")));
    }

    public List<MatchDto> fetchAllOngoingMatchesOfRound(Long roundId) {
        return matchMapper.matchDtoList(matchRepository.findAllByRoundIdAndMatchStatus(roundId, OccurrenceStatus.ONGOING));
    }

    @Transactional
    public MatchDto closeMatch(Long id, MatchDto matchDto) {

        ObjectMapper objectMapper = new ObjectMapper();

        var match = findMatch(id);
        if (match.getMatchStatus().equals(OccurrenceStatus.FINISHED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match already finished");
        }
        var tournament = tournamentRepository.findTournamentByMatch(id).orElseThrow(() -> new EntityNotFoundException("tournament not found"));

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(matchDto.getResultOfMatch());

            var winner = jsonNode.get("winner").asText();
            var setString = jsonNode.get("set").asText();

            validateMatchClosure(tournament, match, winner, setString);
            matchDto.setMatchStatus(OccurrenceStatus.FINISHED.name());
            return matchMapper.toDto(matchRepository.save(matchMapper.fromDto(matchDto)));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "could not parse winner status");
        }
    }

    private void validateMatchClosure(Tournament tournament, Match match, String winner, String setString) {
        Integer set = null;
        try {
            set = Integer.parseInt(setString);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payload does not contain proper set info");
        }

        // validate if winner is valid. Must be one of the two options.
        if (winner.isBlank() || (!match.getPlayerOne().equals(winner) && !match.getPlayerTwo().equals(winner))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payload does not contain proper winner info");
        }

        // validate if the set falls in the range.
        if (set < tournament.getAtpType().minSet || set > tournament.getAtpType().maxSet) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payload does not contain proper set info");
        }
    }

    public List<MatchDto> findAllByRoundId(Long roundId) {
        return matchMapper.matchDtoList(matchRepository.findAllByRoundId(roundId));
    }
}
