package com.acyuta.rf.tournament.core.mappers;

import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.model.Round;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(RoundMapperDecorator.class)
public abstract class RoundMapper {

    @Mapping(target = "occurrenceId", source = "occurrence.id")
    public abstract RoundDto toDto(Round round);

    @Mapping(target = "occurrence", ignore = true) // handled by decorator
    @Mapping(target = "matches", ignore = true)
    public abstract Round fromDto(RoundDto roundDto);
}
