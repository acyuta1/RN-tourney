package com.acyuta.rf.tournament.core.service;

import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    public RoundDto findRound(Long id) {
        return null;
    }
}
