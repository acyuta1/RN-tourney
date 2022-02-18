package com.acyuta.rf.tournament.api.controller;


import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.tournament.core.mappers.MatchMapper;
import com.acyuta.rf.tournament.core.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/round/{roundId}")
    public MatchDto addMatch(@PathVariable("roundId") Long roundId, @RequestBody @Valid MatchDto matchDto) {
        matchDto.setRoundId(roundId);
        return matchService.addMatch(roundId, matchDto);
    }

    @GetMapping("/occurrence/{occurrenceId}/round/{roundId}/match/{matchId}")
    public MatchDto checkMatchExistsAndOngoing(@PathVariable("occurrenceId") Long occurrenceId,
                                               @PathVariable("roundId") Long roundId,
                                               @PathVariable("matchId") Long matchId) {
        return matchService.checkMatchExistsAndOngoing(occurrenceId, roundId, matchId);
    }

    @PutMapping("/{id}/close")
    public MatchDto closeMatches(@PathVariable("id") Long id, @RequestBody MatchDto matchDto) {
        return matchService.closeMatch(id, matchDto);
    }

    @GetMapping("/round/{roundId}")
    public List<MatchDto> findAllByRoundId(@PathVariable("roundId") Long roundId) {
        return matchService.findAllByRoundId(roundId);
    }

}
