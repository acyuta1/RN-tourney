package com.acyuta.rf.tournament.mappers;

import com.acyuta.rf.tournament.dto.OccurrenceDto;
import com.acyuta.rf.tournament.model.Occurrence;
import com.acyuta.rf.tournament.model.Tournament;
import com.acyuta.rf.tournament.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class OccurrenceMapperDecorator extends OccurrenceMapper{

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
