package com.acyuta.rf.tournament.api.controller;

import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.mappers.RoundMapper;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/occurrence/{occurrence_id}")
    public RoundDto addRound(@PathVariable("occurrence_id") Long occurrenceId, @RequestBody RoundDto round) {
        round.setOccurrenceId(occurrenceId);
        round.setRoundStatus("ONGOING");
        return roundService.addRound(occurrenceId, round);
    }
}
