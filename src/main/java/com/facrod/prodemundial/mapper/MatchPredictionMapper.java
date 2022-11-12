package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.MatchPredictionCreateDTO;
import com.facrod.prodemundial.entity.dynamodb.MatchExtra;
import com.facrod.prodemundial.entity.dynamodb.MatchPrediction;
import com.facrod.prodemundial.enums.MatchResult;

public final class MatchPredictionMapper {

    private MatchPredictionMapper() {
    }

    public static MatchPrediction toEntity(MatchPredictionCreateDTO prediction) {
        var predictionEntity = new MatchPrediction();
        predictionEntity.setMatchId(prediction.getMatchId());
        predictionEntity.setHomeScore(prediction.getHomeScore());
        predictionEntity.setAwayScore(prediction.getAwayScore());
        setMatchWinner(prediction, predictionEntity);
        setMatchExtra(prediction, predictionEntity);
        return predictionEntity;
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

}
