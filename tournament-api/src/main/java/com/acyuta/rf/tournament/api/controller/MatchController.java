package com.acyuta.rf.tournament.api.controller;


import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.mappers.MatchMapper;
import com.acyuta.rf.tournament.core.mappers.RoundMapper;
import com.acyuta.rf.tournament.core.service.MatchService;
import com.acyuta.rf.tournament.core.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourney/matches")
public class MatchController {

    private final MatchService matchService;

    private final MatchMapper matchMapper;

    @GetMapping("/{id}")
    public MatchDto fetchMatch(@PathVariable("id") Long id) {
        return matchMapper.toDto(matchService.findMatch(id));
    }

    @PostMapping("/round/{round_id}")
    public MatchDto addRound(@PathVariable("round_id") Long roundId, @RequestBody @Valid MatchDto matchDto) {
        matchDto.setRoundId(roundId);
        return matchService.addMatch(roundId, matchDto);
    }
}
