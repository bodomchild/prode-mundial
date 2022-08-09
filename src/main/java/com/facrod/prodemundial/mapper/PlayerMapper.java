package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.PlayerCreateDTO;
import com.facrod.prodemundial.dto.PlayerResponseDTO;
import com.facrod.prodemundial.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class PlayerMapper {

    private PlayerMapper() {
    }

    public static Player toEntity(PlayerCreateDTO player) {
        var entity = new Player();
        entity.setId(player.getId());
        entity.setTeamId(player.getTeamId());
        entity.setName(player.getName());
        entity.setPosition(player.getPosition());
        entity.setAge(player.getAge());
        return entity;
    }

    public static PlayerResponseDTO toDto(Player player) {
        var dto = new PlayerResponseDTO();
        dto.setId(player.getId());
        dto.setTeamId(player.getTeamId());
        dto.setName(player.getName());
        dto.setPosition(player.getPosition());
        dto.setAge(player.getAge());
        dto.setGoals(player.getGoals());
        dto.setYellowCards(player.getYellowCards());
        dto.setRedCards(player.getRedCards());
        return dto;
    }

    public static List<PlayerResponseDTO> toDto(List<Player> players) {
        return players.stream().map(PlayerMapper::toDto).collect(Collectors.toList());
    }

}
