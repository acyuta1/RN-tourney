package com.acyuta.rf.tournament.core.mappers;

import com.acyuta.rf.rafantasyShared.dto.tourney.OccurrenceDto;
import com.acyuta.rf.tournament.core.model.Occurrence;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
@DecoratedWith(OccurrenceMapperDecorator.class)
public abstract class OccurrenceMapper {

    @Mapping(target = "tournamentId", source = "tournament.id")
    public abstract OccurrenceDto toDto(Occurrence occurrence);

    @Mapping(target = "tournament", ignore = true) // handled by decorator
    @Mapping(target = "rounds", ignore = true)
    public abstract Occurrence fromDto(OccurrenceDto occurrenceDto);

    public abstract List<OccurrenceDto> toDto(List<Occurrence> occurrences);
}
