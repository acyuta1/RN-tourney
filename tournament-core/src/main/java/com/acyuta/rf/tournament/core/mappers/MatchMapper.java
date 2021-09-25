package com.acyuta.rf.tournament.core.mappers;

import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.model.Match;
import com.acyuta.rf.tournament.core.model.Round;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(MatchMapperDecorator.class)
public abstract class MatchMapper {

    @Mapping(target = "roundId", source = "round.id")
    public abstract MatchDto toDto(Match match);

    @Mapping(target = "round", ignore = true) // handled by decorator
    public abstract Match fromDto(MatchDto matchDto);
}
