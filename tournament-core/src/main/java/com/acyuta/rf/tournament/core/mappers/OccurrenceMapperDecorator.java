package com.acyuta.rf.tournament.core.mappers;

import com.acyuta.rf.rafantasyShared.dto.tourney.OccurrenceDto;
import com.acyuta.rf.tournament.core.model.Occurrence;
import com.acyuta.rf.tournament.core.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class OccurrenceMapperDecorator extends OccurrenceMapper {

    @Autowired
    @Qualifier("delegate")
    private OccurrenceMapper delegate;

    @Autowired
    private TournamentService tournamentService;

    @Override
    public Occurrence fromDto(OccurrenceDto occurrenceDto) {
        var occurrence = delegate.fromDto(occurrenceDto);

        if(occurrenceDto.getTournamentId() != null) {
            var tournament = tournamentService.findById(occurrenceDto.getTournamentId());

            occurrence.setTournament(tournament);
        }
        return occurrence;
    }
}
