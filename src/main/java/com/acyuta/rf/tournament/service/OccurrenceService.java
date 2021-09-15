package com.acyuta.rf.tournament.service;

import com.acyuta.rf.tournament.dto.OccurrenceDto;
import com.acyuta.rf.tournament.mappers.OccurrenceMapper;
import com.acyuta.rf.tournament.model.Occurrence;
import com.acyuta.rf.tournament.model.OccurrenceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
@RequiredArgsConstructor
public class OccurrenceService {

    private final OccurrenceRepository occurrenceRepository;

    private final OccurrenceMapper occurrenceMapper;

    private final TournamentService tournamentService;

    public OccurrenceDto findOccurrence(Long id) {
        return occurrenceMapper.toDto(occurrenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "occurrence.not.found")));
    }

    public OccurrenceDto create(Long tournamentId) {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(firstDayOfYear());
        LocalDate lastDay = now.with(lastDayOfYear());

        var tournament = tournamentService.findById(tournamentId);

        var isPresent = occurrenceRepository.existsByOccurrenceDateBetween(firstDay, lastDay);

        if (!isPresent) {
            var occurrence = new Occurrence();
            occurrence
                    .setTournament(tournament)
                    .setOccurrenceDate(now)
                    .setOccurrenceStatus(OccurrenceStatus.ONGOING);

            return occurrenceMapper.toDto(occurrenceRepository.save(occurrence));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "occurrence already exists in the current year");
    }
}
