package com.facrod.prodemundial.enums;

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

    private static MatchResult getPenaltiesRoundWinner(WCPenaltiesRound penaltiesRound) {
        if (penaltiesRound.getHomeTeamScore() > penaltiesRound.getAwayTeamScore()) {
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

}
