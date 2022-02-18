package com.acyuta.rf.tournament.core.mappers;

import com.acyuta.rf.rafantasyShared.dto.tourney.PlayerDto;
import com.acyuta.rf.tournament.core.model.Player;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class PlayerMapper {

    public abstract PlayerDto toDto(Player player);

    public abstract Player fromDto(PlayerDto playerDto);

    public abstract List<PlayerDto> toDtoList(List<Player> players);
}
