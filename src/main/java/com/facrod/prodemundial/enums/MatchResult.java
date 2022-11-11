package com.facrod.prodemundial.enums;

import com.facrod.prodemundial.dto.PredictionCreateDTO;
import com.facrod.prodemundial.entity.WCMatch;
import com.facrod.prodemundial.entity.WCPenaltiesRound;

public enum MatchResult {

    HOME_WIN,
    DRAW,
    AWAY_WIN;

    public static MatchResult getMatchResult(WCMatch match) {
        if (match.isPenalties()) {
            return getPenaltiesRoundWinner(match.getPenaltiesRound());
        }

        if (match.isExtraTime()) {
            return getExtraTimeWinner(match);
        }

        if (match.getHomeScore() > match.getAwayScore()) {
            return HOME_WIN;
        } else if (match.getHomeScore() < match.getAwayScore()) {
            return AWAY_WIN;
        }
        return DRAW;
    }

    public static MatchResult getMatchResult(PredictionCreateDTO prediction) {
        if (prediction.getPenalties() != null) {
            return getPenaltiesRoundWinner(prediction.getPenalties());
        }

        if (prediction.getExtraTime() != null) {
            return getExtraTimeWinner(prediction.getExtraTime());
        }

        if (prediction.getHomeScore() > prediction.getAwayScore()) {
            return HOME_WIN;
        } else if (prediction.getHomeScore() < prediction.getAwayScore()) {
            return AWAY_WIN;
        }
        return DRAW;
    }

    private static MatchResult getPenaltiesRoundWinner(WCPenaltiesRound penaltiesRound) {
        if (penaltiesRound.getHomeTeamScore() > penaltiesRound.getAwayTeamScore()) {
            return HOME_WIN;
        }
        return AWAY_WIN;
    }

    private static MatchResult getPenaltiesRoundWinner(PredictionCreateDTO.MatchExtraDTO predictionPenaltiesRound) {
        if (predictionPenaltiesRound.getHomeScore() > predictionPenaltiesRound.getAwayScore()) {
            return HOME_WIN;
        }
        return AWAY_WIN;
    }

    private static MatchResult getExtraTimeWinner(WCMatch match) {
        if (match.getExtraTimeHomeScore() > match.getExtraTimeAwayScore()) {
            return HOME_WIN;
        }
        return AWAY_WIN;
    }

    private static MatchResult getExtraTimeWinner(PredictionCreateDTO.MatchExtraDTO predictionExtraTime) {
        if (predictionExtraTime.getHomeScore() > predictionExtraTime.getAwayScore()) {
            return HOME_WIN;
        }
        return AWAY_WIN;
    }

}
