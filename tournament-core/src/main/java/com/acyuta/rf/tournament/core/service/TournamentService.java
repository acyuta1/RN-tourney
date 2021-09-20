package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.tournament.core.model.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public List<Tournament> fetchAll() {
        return tournamentRepository.findAll();
    }

    public Tournament findById(Long tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("tournament with ID - %s not found", tournamentId)));
    }
}
