package com.acyuta.rf.tournament.core.mappers;


import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.model.Match;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.service.OccurrenceService;
import com.acyuta.rf.tournament.core.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class MatchMapperDecorator extends MatchMapper {

    @Autowired
    @Qualifier("delegate")
    private MatchMapper delegate;

    @Autowired
    private RoundService roundService;

    @Override
    public Match fromDto(MatchDto matchDto) {
        var match = delegate.fromDto(matchDto);

        if(matchDto.getRoundId() != null) {
            var round = roundService.findRound(matchDto.getRoundId());

            match.setRound(round);
        }
        return match;
    }
}
