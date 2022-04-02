package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.entity.WCTeam;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class TeamMapper {

    private TeamMapper() {
    }

    public static TeamDTO toDto(WCTeam team) {
        var dto = new TeamDTO();
        BeanUtils.copyProperties(team, dto);
        dto.setGroup(team.getWorldCupGroup());
        return dto;
    }

    public static WCTeam toEntity(TeamDTO dto) {
        var entity = new WCTeam();
        BeanUtils.copyProperties(dto, entity);
        entity.setWorldCupGroup(dto.getGroup());
        return entity;
    }

    public static List<TeamDTO> toDto(List<WCTeam> teams) {
        return teams.stream().map(TeamMapper::toDto).collect(Collectors.toList());
    }

}
