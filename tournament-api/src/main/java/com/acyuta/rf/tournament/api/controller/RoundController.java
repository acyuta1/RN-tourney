package com.acyuta.rf.tournament.api.controller;

import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.mappers.RoundMapper;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.model.RoundType;
import com.acyuta.rf.tournament.core.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourney/rounds")
public class RoundController {

    private final RoundService roundService;

    private final RoundMapper roundMapper;

    @GetMapping("/{id}")
    public RoundDto fetchRound(@PathVariable("id") Long id) {
        return roundMapper.toDto(roundService.findRound(id));
    }

    @GetMapping("/occurrence/{id}")
    public List<RoundDto> fetchRoundsByOccurrence(@PathVariable("id") Long occurrenceId) {
        return roundMapper.toDtoList(roundService.fetchRoundsByOccurrence(occurrenceId));
    }

    @PostMapping("/occurrence/{occurrence_id}")
    public RoundDto addRound(@PathVariable("occurrence_id") Long occurrenceId, @RequestBody RoundDto round) {
        round.setOccurrenceId(occurrenceId);
        round.setRoundStatus("ONGOING");
        return roundService.addRound(occurrenceId, round);
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<String> closeRound(@PathVariable("id") Long id) {
        return roundService.closeRound(id);
    }

    @GetMapping("/{id}/matches")
    public List<MatchDto> fetchMatchesByRound(@PathVariable("id") Long id) {
        return roundService.fetchMatchesByRound(id);
    }

    @GetMapping("/round-types")
    public List<String> getRoundTypes() {
        return Arrays.stream(RoundType.values()).map(RoundType::toString).collect(Collectors.toList());
    }
}
