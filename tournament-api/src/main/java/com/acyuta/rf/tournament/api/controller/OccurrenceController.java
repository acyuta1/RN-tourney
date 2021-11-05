package com.acyuta.rf.tournament.api.controller;

import com.acyuta.rf.rafantasyShared.dto.tourney.OccurrenceDto;
import com.acyuta.rf.tournament.core.model.Occurrence;
import com.acyuta.rf.tournament.core.service.OccurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

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

    @PutMapping("/{id}/close")
    public ResponseEntity<String> closeOccurrence(@PathVariable("id") Long id) {
        return occurrenceService.closeOccurrence(id);
    }

    @GetMapping("/{id}/prev-occurrence/{tournament_id}")
    public OccurrenceDto fetchPreviousOccurrence(@PathVariable("id") Long id, @PathVariable("tournament_id") Long tournamentId) {
        return occurrenceService.fetchPreviousOccurrence(id, tournamentId);
    }

    @GetMapping("/{id}/get-points-distribution")
    @Transactional
    public List<Integer> getPointsDistribution(@PathVariable("id") Occurrence occurrence) {
        return occurrence.getTournament().getAtpType().points;
    }

    @PutMapping("/{id}/winner/{userId}")
    public OccurrenceDto declareWinnerAndCloseOccurrence(@PathVariable("id") Occurrence occurrence,
                                                         @PathVariable("userId") Long userId) {
        return occurrenceService.declareWinnerAndCloseOccurrence(occurrence, userId);
    }
}
