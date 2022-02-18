package com.acyuta.rf.tournament.api.controller;

import com.acyuta.rf.rafantasyShared.dto.tourney.PlayerDto;
import com.acyuta.rf.tournament.core.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourney/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/init")
    public void intPlayers() {
        playerService.initPlayers();
    }

    @GetMapping()
    public List<PlayerDto> getPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("/{id}")
    public PlayerDto findById(@PathVariable("id") Long playerId) {
        return playerService.findPlayer(playerId);
    }
}
