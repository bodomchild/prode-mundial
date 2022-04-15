package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.MatchCreateDTO;
import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.entity.WCMatch;
import com.facrod.prodemundial.enums.MatchResult;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class MatchMapper {

    private MatchMapper() {
    }

    public static MatchResponseDTO toMatchResponseDto(WCMatch match) {
        var dto = new MatchResponseDTO();
        dto.setId(match.getId());
        dto.setStartTime(match.getStartTime());

        dto.setHomeTeam(MatchResponseDTO.TeamDTO.builder()
                .id(match.getHomeTeam().getId())
                .name(match.getHomeTeam().getName())
                .build());

        dto.setAwayTeam(MatchResponseDTO.TeamDTO.builder()
                .id(match.getAwayTeam().getId())
                .name(match.getAwayTeam().getName())
                .build());

        if (match.isFinished()) {
            dto.setFinished(match.isFinished());
            dto.setHomeScore(match.getHomeScore());
            dto.setAwayScore(match.getAwayScore());
            dto.setResult(MatchResult.getMatchResult(match));

            if (match.isExtraTime()) {
                dto.setExtraTime(match.isExtraTime());
                dto.setExtraTimeHomeScore(match.getExtraTimeHomeScore());
                dto.setExtraTimeAwayScore(match.getExtraTimeAwayScore());
                dto.setPenalties(match.isPenalties());

                if (match.isPenalties()) {
                    dto.setPenaltiesRound(PenaltiesRoundMapper.toDto(match.getPenaltiesRound()));
                }
            }
        }


        return dto;
    }

    public static WCMatch matchCreateDtoToEntity(MatchCreateDTO match) {
        var entity = new WCMatch();
        BeanUtils.copyProperties(match, entity, "homeTeam", "awayTeam");
        return entity;
    }

    public static List<MatchResponseDTO> toMatchResponseDto(List<WCMatch> matches) {
        return matches.stream().map(MatchMapper::toMatchResponseDto).collect(Collectors.toList());
    }

}
