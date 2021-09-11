package com.acyuta.rf.tournament.mappers;

import com.acyuta.rf.tournament.dto.OccurrenceDto;
import com.acyuta.rf.tournament.model.Occurrence;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(OccurrenceMapperDecorator.class)
public abstract class OccurrenceMapper {

    @Mapping(target = "tournamentId", source = "tournament.id")
    public abstract OccurrenceDto toDto(Occurrence occurrence);

    @Mapping(target = "tournament", ignore = true) // handled by decorator
    public abstract Occurrence fromDto(OccurrenceDto occurrenceDto);
}
