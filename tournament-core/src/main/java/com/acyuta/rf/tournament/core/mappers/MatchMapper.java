package com.acyuta.rf.tournament.core.mappers;

import com.acyuta.rf.rafantasyShared.dto.tourney.MatchDto;
import com.acyuta.rf.rafantasyShared.dto.tourney.MatchPlayerResponse;
import com.acyuta.rf.rafantasyShared.dto.tourney.RoundDto;
import com.acyuta.rf.tournament.core.model.Match;
import com.acyuta.rf.tournament.core.model.Round;
import com.acyuta.rf.tournament.core.service.PlayerService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
@DecoratedWith(MatchMapperDecorator.class)
public abstract class MatchMapper {

    @Autowired
    PlayerService playerService;

    @Mapping(target = "roundId", source = "round.id")
    @Mapping(target = "playerResponse", ignore = true)
    public abstract MatchDto toDto(Match match);

    @Mapping(target = "round", ignore = true)
    public abstract Match fromDto(MatchDto matchDto);

    public abstract List<MatchDto> matchDtoList(List<Match> matches);

    @AfterMapping
    protected void findPlayerResponse(@MappingTarget MatchDto matchDto) {
        if(matchDto.getPlayerOne() != null && matchDto.getPlayerTwo() != null) {
            var playerInfo = new MatchPlayerResponse();
            var playerOne = playerService.findPlayer(matchDto.getPlayerOne());
            var playerTwo = playerService.findPlayer(matchDto.getPlayerTwo());
            playerInfo.setPlayerOneName(playerOne.getFullName())
                    .setPlayerTwoName(playerTwo.getFullName());
            matchDto.setPlayerResponse(playerInfo);
        }
    }

}
