package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.PenaltiesRoundDTO;
import com.facrod.prodemundial.entity.WCPenaltiesRound;
import com.facrod.prodemundial.entity.WCPenalty;

import java.util.List;
import java.util.stream.Collectors;

public final class PenaltiesRoundMapper {

    private PenaltiesRoundMapper() {
    }

    public static PenaltiesRoundDTO toDto(WCPenaltiesRound penaltiesRound) {
        var dto = new PenaltiesRoundDTO();
        dto.setHomeTeamStarted(penaltiesRound.isHomeTeamStarted());
        dto.setHomeTeamScore(penaltiesRound.getHomeTeamScore());
        dto.setAwayTeamScore(penaltiesRound.getAwayTeamScore());
        dto.setHomeTeamPenalties(PenaltyMapper.toDto(penaltiesRound.getHomeTeamPenalties()));
        dto.setAwayTeamPenalties(PenaltyMapper.toDto(penaltiesRound.getAwayTeamPenalties()));
        return dto;
    }

    public static WCPenaltiesRound toEntity(PenaltiesRoundDTO dto) {
        var entity = new WCPenaltiesRound();
        entity.setHomeTeamStarted(dto.isHomeTeamStarted());
        entity.setHomeTeamScore(dto.getHomeTeamScore());
        entity.setAwayTeamScore(dto.getAwayTeamScore());
        entity.setHomeTeamPenalties(PenaltyMapper.toEntity(dto.getHomeTeamPenalties()));
        entity.setAwayTeamPenalties(PenaltyMapper.toEntity(dto.getAwayTeamPenalties()));
        return entity;
    }

    private static class PenaltyMapper {

        private PenaltyMapper() {
        }

        private static PenaltiesRoundDTO.Penalty toDto(WCPenalty penalty) {
            var dto = new PenaltiesRoundDTO.Penalty();
            dto.setOrder(penalty.getOrder());
            dto.setScored(penalty.isScored());
            return dto;
        }

        private static WCPenalty toEntity(PenaltiesRoundDTO.Penalty dto) {
            var entity = new WCPenalty();
            entity.setOrder(dto.getOrder());
            entity.setScored(dto.isScored());
            return entity;
        }

        public static List<PenaltiesRoundDTO.Penalty> toDto(List<WCPenalty> penalties) {
            return penalties.stream().map(PenaltyMapper::toDto).collect(Collectors.toList());
        }

        public static List<WCPenalty> toEntity(List<PenaltiesRoundDTO.Penalty> dtos) {
            return dtos.stream().map(PenaltyMapper::toEntity).collect(Collectors.toList());
        }

    }

}
