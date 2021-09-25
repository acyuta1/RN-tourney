package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.mappers.RoundMapper;
import com.acyuta.rf.tournament.core.model.OccurrenceStatus;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.model.RoundType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    private final OccurrenceService occurrenceService;

    private final RoundMapper roundMapper;

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
        round.setRoundValue(round.getRoundType().ordinal());
        return roundMapper.toDto(roundRepository.save(round));
    }
}
