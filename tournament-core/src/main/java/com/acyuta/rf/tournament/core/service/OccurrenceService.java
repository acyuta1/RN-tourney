package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.rafantasyShared.dto.tourney.OccurrenceDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.OccurrenceFinishedDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundFinishedDto;
import com.acyuta.rf.rafantasyShared.feign.config.UserClient;
import com.acyuta.rf.tournament.core.mappers.OccurrenceMapper;
import com.acyuta.rf.tournament.core.messaging.MessagePublisher;
import com.acyuta.rf.tournament.core.model.Occurrence;
import com.acyuta.rf.tournament.core.model.OccurrenceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final RoundRepository roundRepository;

    private final MessagePublisher messagePublisher;

    private final UserClient userClient;

    public Occurrence findById(Long id) {
        return occurrenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "occurrence.not.found"));
    }

    public OccurrenceDto findOccurrence(Long id) {
        return occurrenceMapper.toDto(occurrenceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "occurrence.not.found")));
    }

    public OccurrenceDto create(Long tournamentId) {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(firstDayOfYear());
        LocalDate lastDay = now.with(lastDayOfYear());

        var tournament = tournamentService.findById(tournamentId);

        var isPresent = occurrenceRepository.existsByTournamentIdAndOccurrenceDateBetween(tournamentId, firstDay, lastDay);

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

    public Boolean isOccurrenceActive(Long id) {
        var occurrence = this.findOccurrence(id);
        System.out.println(occurrence);
        return OccurrenceStatus.ONGOING.equals(OccurrenceStatus.valueOf(occurrence.getOccurrenceStatus()));
    }

    public ResponseEntity<String> closeOccurrence(Long id) {
        var occurrence = findById(id);

        if ((OccurrenceStatus.TBD.equals(occurrence.getOccurrenceStatus()))
            || OccurrenceStatus.FINISHED.equals(occurrence.getOccurrenceStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "occurrence closed already.");
        }

        var ongoingRounds = roundRepository.findAllByOccurrenceIdAndRoundStatus(occurrence.getId(), OccurrenceStatus.ONGOING);

        if (ongoingRounds.size() != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rounds must be closed before an occurrence closes.");
        }

        occurrence.setOccurrenceStatus(OccurrenceStatus.TBD);

        occurrenceRepository.save(occurrence);

        var occurrenceFinishedDto = new OccurrenceFinishedDto();
        occurrenceFinishedDto.setOccurrenceId(id);
        occurrenceFinishedDto.setTournamentId(occurrence.getTournament().getId());

        messagePublisher.publishOccurrenceFinished(occurrenceFinishedDto);

        return ResponseEntity.ok(String.format("Initiated scoring for occurrence - %s", id));
    }

    public OccurrenceDto fetchPreviousOccurrence(Long id, Long tournamentId) {

        LocalDate now = LocalDate.now().minusYears(1);
        LocalDate firstDay = now.with(firstDayOfYear());
        LocalDate lastDay = now.with(lastDayOfYear());

        var occurrence = findOccurrence(id);

        var prevOccurrence = occurrenceRepository
                .findByTournamentIdAndOccurrenceDateBetween(tournamentId, firstDay, lastDay);

        return prevOccurrence.map(occurrenceMapper::toDto).orElse(null);
    }

    public OccurrenceDto declareWinnerAndCloseOccurrence(Occurrence occurrence, Long userId) {

        var user = userClient.fetchById(userId);

        occurrence.setWinnerId(userId);
        occurrence.setOccurrenceStatus(OccurrenceStatus.FINISHED);

        return occurrenceMapper.toDto(occurrenceRepository.save(occurrence));
    }
}
