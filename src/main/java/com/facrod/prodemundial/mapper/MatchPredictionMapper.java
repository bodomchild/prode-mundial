package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.MatchExtraDTO;
import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;
import com.facrod.prodemundial.dto.MatchPredictionResponseDTO;
import com.facrod.prodemundial.dto.ScorerDTO;
import com.facrod.prodemundial.entity.dynamodb.MatchExtra;
import com.facrod.prodemundial.entity.dynamodb.MatchPrediction;
import com.facrod.prodemundial.entity.dynamodb.Scorer;
import com.facrod.prodemundial.enums.MatchResult;

import java.util.List;
import java.util.stream.Collectors;

public final class MatchPredictionMapper {

    private MatchPredictionMapper() {
    }

    public static MatchPrediction toEntity(MatchPredictionCreateDTO prediction) {
        var predictionEntity = new MatchPrediction();
        predictionEntity.setMatchId(prediction.getMatchId());
        predictionEntity.setHomeScore(prediction.getHomeScore());
        predictionEntity.setAwayScore(prediction.getAwayScore());
        if (prediction.getHomeTeamScorers() != null) {
            predictionEntity.setHomeTeamScorers(ScorerMapper.toEntity(prediction.getHomeTeamScorers()));
        }
        if (prediction.getAwayTeamScorers() != null) {
            predictionEntity.setAwayTeamScorers(ScorerMapper.toEntity(prediction.getAwayTeamScorers()));
        }
        setMatchWinner(prediction, predictionEntity);
        setMatchExtra(prediction, predictionEntity);
        return predictionEntity;
    }

    public static MatchPredictionResponseDTO toResponseDTO(MatchPrediction prediction) {
        var predictionDTO = new MatchPredictionResponseDTO();
        predictionDTO.setUsername(prediction.getUsername());
        predictionDTO.setMatchId(prediction.getMatchId());
        predictionDTO.setWinner(prediction.getWinner());
        predictionDTO.setHomeScore(prediction.getHomeScore());
        predictionDTO.setAwayScore(prediction.getAwayScore());

        if (prediction.getExtraTime() != null) {
            var extraTime = new MatchExtraDTO();
            extraTime.setHomeScore(prediction.getExtraTime().getHomeScore());
            extraTime.setAwayScore(prediction.getExtraTime().getAwayScore());
            predictionDTO.setExtraTime(extraTime);
        }

        if (prediction.getPenalties() != null) {
            var penalties = new MatchExtraDTO();
            penalties.setHomeScore(prediction.getPenalties().getHomeScore());
            penalties.setAwayScore(prediction.getPenalties().getAwayScore());
            predictionDTO.setPenalties(penalties);
        }

        return predictionDTO;
    }

    private static void setMatchWinner(MatchPredictionCreateDTO prediction, MatchPrediction matchPredictionEntity) {
        var matchWinner = MatchResult.getMatchResult(prediction);
        matchPredictionEntity.setWinner(matchWinner.name());
    }

    private static void setMatchExtra(MatchPredictionCreateDTO prediction, MatchPrediction matchPredictionEntity) {
        if (prediction.getExtraTime() != null) {
            var extraTime = new MatchExtra(prediction.getExtraTime().getHomeScore(), prediction.getExtraTime().getAwayScore());
            matchPredictionEntity.setExtraTime(extraTime);
        }
        if (prediction.getPenalties() != null) {
            var penalties = new MatchExtra(prediction.getPenalties().getHomeScore(), prediction.getPenalties().getAwayScore());
            matchPredictionEntity.setPenalties(penalties);
        }
    }

    private static class ScorerMapper {
        public static List<Scorer> toEntity(List<ScorerDTO> scorers) {
            return scorers.stream()
                    .map(scorer -> new Scorer(scorer.getId(), scorer.getGoals()))
                    .collect(Collectors.toList());
        }
    }

}
