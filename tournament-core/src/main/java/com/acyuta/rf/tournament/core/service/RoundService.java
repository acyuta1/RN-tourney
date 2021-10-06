package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundFinishedDto;
import com.acyuta.rf.tournament.core.mappers.MatchMapper;
import com.acyuta.rf.tournament.core.mappers.RoundMapper;
import com.acyuta.rf.tournament.core.messaging.MessagePublisher;
import com.acyuta.rf.tournament.core.model.OccurrenceStatus;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.model.RoundType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    private final OccurrenceService occurrenceService;

    private final RoundMapper roundMapper;

    private final MatchRepository matchRepository;

    private final MessagePublisher messagePublisher;

    private final MatchMapper matchMapper;

    public Round findRound(Long id) {
        return roundRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "round not found"));
    }

    public RoundDto addRound(Long occurrenceId, RoundDto roundDto) {

        // 1. occurrence exists or not!
        var occurrenceDto = occurrenceService.findOccurrence(occurrenceId);
        // 2. occurrence status must be ongoing!

        if (!OccurrenceStatus.ONGOING.equals(OccurrenceStatus.valueOf(occurrenceDto.getOccurrenceStatus()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "occurrence.has.finished.already!!!!!");
        }

        // 3. check if previous round exists. If present, check if the status OF THE ROUND is not ONGOING.
        var prevRound = roundRepository.
                findAllByOccurrenceIdAndRoundStatus(
                        occurrenceDto.getId(),
                        OccurrenceStatus.ONGOING
                );

        if (prevRound.size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "previous.rounds.still.ongoing!!!!!");
        }

        if (roundRepository.findByOccurrenceIdAndRoundType(occurrenceDto.getId(), RoundType.valueOf(roundDto.getRoundType())).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("round.with.round.type - %s already.present", roundDto.getRoundType()));
        }

        // On passing all the conditions, we  create a new round.
        var round = roundMapper.fromDto(roundDto);
        return roundMapper.toDto(roundRepository.save(round));
    }

    @Transactional
    public ResponseEntity<String> closeRound(Long id) {

        // 1. check if all matches are closed already.
        var round = findRound(id);

        if (round.getRoundStatus().equals(OccurrenceStatus.FINISHED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "round closed already.");
        }

        var ongoingMatches = matchRepository.findAllByRoundIdAndMatchStatus(id, OccurrenceStatus.ONGOING);

        if (ongoingMatches.size() != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "matches must be closed before a round closes.");
        }

        round.setRoundStatus(OccurrenceStatus.FINISHED);

        roundRepository.save(round);

        var roundFinishedDto = new RoundFinishedDto();
        roundFinishedDto.setRoundId(id);
        roundFinishedDto.setOccurrenceId(round.getOccurrence().getId());

        messagePublisher.publishRoundFinishedInit(roundFinishedDto);

        return ResponseEntity.ok(String.format("Initiated scoring for round - %s", id));
    }

    public List<MatchDto> fetchMatchesByRound(Long id) {
        return matchMapper.matchDtoList(matchRepository.findAllByRoundId(id));
    }
}
