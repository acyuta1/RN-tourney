package com.acyuta.rf.tournament.api.controller;

import com.acyuta.rf.rafantasyShared.dto.external.PageRequestDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.TournamentDto;
import com.acyuta.rf.tournament.api.config.TournamentConfigProps;
import com.acyuta.rf.tournament.core.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tourney/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    private final TournamentConfigProps tournamentConfigProps;

    @GetMapping
    public Page<TournamentDto> fetchAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return tournamentService.fetchAll(page, size);
    }

    @PostMapping("/add-tournament")
    public TournamentDto uploadFile(@RequestParam(value = "file", required = false) MultipartFile image, @RequestBody TournamentDto tournamentDto) {
        return tournamentService.addTournament(tournamentDto, image, tournamentConfigProps.getUploadDir());
    }

    @PostMapping("/init")
    public void initTournaments() {
        tournamentService.initTournaments();
    }
}
