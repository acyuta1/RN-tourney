package com.acyuta.rf.tournament.controller;

import com.acyuta.rf.tournament.model.Tournament;
import com.acyuta.rf.tournament.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tourney/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    public List<Tournament> fetchAll() {
        return tournamentService.fetchAll();
    }

}