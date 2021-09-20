package com.acyuta.rf.tournament.core.mappers;

import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.service.OccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class RoundMapperDecorator extends RoundMapper {

    @Autowired
    @Qualifier("delegate")
    private RoundMapper delegate;

    @Autowired
    private OccurrenceService occurrenceService;

    @Override
    public Round fromDto(RoundDto roundDto) {
        var round = delegate.fromDto(roundDto);

        if(roundDto.getOccurrenceId() != null) {
            var occurrence = occurrenceService.findById(roundDto.getOccurrenceId());

            round.setOccurrence(occurrence);
        }
        return round;
    }
}
