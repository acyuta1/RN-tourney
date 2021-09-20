package com.acyuta.rf.tournament.api.controller;

import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourney/rounds")
public class RoundController {

    private final RoundService roundService;

    @GetMapping("/{id}")
    public RoundDto fetchOccurrence(@PathVariable("id") Long id) {
        return roundService.findRound(id);
    }
}
