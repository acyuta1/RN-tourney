package com.acyuta.rf.tournament.core.mappers;

import com.acyuta.rf.rafantasyShared.dto.tourney.TournamentDto;
import com.acyuta.rf.tournament.core.model.Tournament;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {OccurrenceMapper.class})
public abstract class TournamentMapper {

    public abstract TournamentDto toDto(Tournament tournament);

    public abstract Tournament fromDto(TournamentDto tournamentDto);

    public abstract List<Tournament> fromDtoList(List<TournamentDto> tournamentDtos);

    public abstract List<TournamentDto> fromList(List<Tournament> tournaments);
}
