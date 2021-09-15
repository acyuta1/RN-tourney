package com.acyuta.rf.tournament.controller;

import com.acyuta.rf.tournament.dto.OccurrenceDto;
import com.acyuta.rf.tournament.model.Occurrence;
import com.acyuta.rf.tournament.service.OccurrenceService;
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

    @PostMapping("/tournament/{tournamentId}")
    public OccurrenceDto create(@PathVariable("tournamentId") Long tournamentId) {
        return occurrenceService.create(tournamentId);
    }
}
