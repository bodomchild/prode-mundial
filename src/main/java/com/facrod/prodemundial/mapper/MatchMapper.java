package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.MatchDTO;
import com.facrod.prodemundial.entity.WCMatch;
import com.facrod.prodemundial.enums.MatchResult;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class MatchMapper {

    private MatchMapper() {
    }

    public static MatchDTO toDto(WCMatch match) {
        var dto = new MatchDTO();
        BeanUtils.copyProperties(match, dto, "homeTeam", "awayTeam", "result", "penaltiesRound");
        dto.setHomeTeam(MatchDTO.TeamDTO.builder()
                .id(match.getHomeTeam().getId())
                .name(match.getHomeTeam().getName())
                .build());
        dto.setAwayTeam(MatchDTO.TeamDTO.builder()
                .id(match.getAwayTeam().getId())
                .name(match.getAwayTeam().getName())
                .build());

        if (match.isFinished()) {
            dto.setResult(MatchResult.getMatchResult(match));
        }

        return dto;
    }

    public static WCMatch toEntity(MatchDTO match) {
        var entity = new WCMatch();
        BeanUtils.copyProperties(match, entity, "homeTeam", "awayTeam");
        return entity;
    }

    public static List<MatchDTO> toDto(List<WCMatch> matches) {
        return matches.stream().map(MatchMapper::toDto).collect(Collectors.toList());
    }

}
