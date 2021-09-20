package com.acyuta.rf.tournament.api.controller;

import com.acyuta.rf.rafantasyShared.dto.tourney.OccurrenceDto;
import com.acyuta.rf.tournament.core.service.OccurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourney/occurrences")
@RequiredArgsConstructor
public class OccurrenceController {

    private final OccurrenceService occurrenceService;

    @GetMapping("/{id}")
    public OccurrenceDto fetchOccurrence(@PathVariable("id") Long id) {
        return occurrenceService.findOccurrence(id);
    }

    @GetMapping("/{id}/isActive")
    public Boolean isOccurrenceActive(@PathVariable("id") Long id) {
        return occurrenceService.isOccurrenceActive(id);
    }

    @PostMapping("/tournament/{tournamentId}")
    public OccurrenceDto create(@PathVariable("tournamentId") Long tournamentId) {
        return occurrenceService.create(tournamentId);
    }
}
