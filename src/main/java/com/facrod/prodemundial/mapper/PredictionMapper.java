package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.PredictionCreateDTO;
import com.facrod.prodemundial.entity.dynamodb.MatchExtra;
import com.facrod.prodemundial.entity.dynamodb.Prediction;
import com.facrod.prodemundial.enums.MatchExtraType;
import com.facrod.prodemundial.enums.MatchResult;

public final class PredictionMapper {

    private PredictionMapper() {
    }

    public static Prediction toEntity(PredictionCreateDTO prediction) {
        var predictionEntity = new Prediction();
        predictionEntity.setMatchId(prediction.getMatchId());
        predictionEntity.setHomeScore(prediction.getHomeScore());
        predictionEntity.setAwayScore(prediction.getAwayScore());
        setMatchWinner(prediction, predictionEntity);
        setMatchExtra(prediction, predictionEntity);
        return predictionEntity;
    }

    private static void setMatchWinner(PredictionCreateDTO prediction, Prediction predictionEntity) {
        var matchWinner = MatchResult.getMatchResult(prediction);
        predictionEntity.setWinner(matchWinner.name());
    }

    private static void setMatchExtra(PredictionCreateDTO prediction, Prediction predictionEntity) {
        if (prediction.getExtraTime() != null) {
            var extraTime = new MatchExtra(MatchExtraType.ET, prediction.getExtraTime().getHomeScore(), prediction.getExtraTime().getAwayScore());
            predictionEntity.setExtraTime(extraTime);
        }
        if (prediction.getPenalties() != null) {
            var penalties = new MatchExtra(MatchExtraType.P, prediction.getPenalties().getHomeScore(), prediction.getPenalties().getAwayScore());
            predictionEntity.setPenalties(penalties);
        }
    }

}
