package com.acyuta.rf.tournament.mappers;

import com.acyuta.rf.tournament.dto.TournamentDto;
import com.acyuta.rf.tournament.model.Tournament;
import org.mapstruct.Mapper;

@Mapper(uses = {OccurrenceMapper.class})
public abstract class TournamentMapper {

    public abstract TournamentDto toDto(Tournament tournament);

    public abstract Tournament fromDto(TournamentDto tournamentDto);
}
