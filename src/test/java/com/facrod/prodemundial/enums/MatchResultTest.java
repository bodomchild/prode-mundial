package com.facrod.prodemundial.enums;

import com.facrod.prodemundial.entity.WCMatch;
import com.facrod.prodemundial.entity.WCPenaltiesRound;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MatchResultTest {

    @Test
    void getMatchResult_homeWin() {
        var match = new WCMatch();
        match.setHomeScore(2);
        match.setAwayScore(1);

        var result = MatchResult.getMatchResult(match);

        assertNotNull(result);
        assertEquals(MatchResult.HOME_WIN, result);
    }

    @Test
    void getMatchResult_awayWin() {
        var match = new WCMatch();
        match.setHomeScore(1);
        match.setAwayScore(2);

        var result = MatchResult.getMatchResult(match);

        assertNotNull(result);
        assertEquals(MatchResult.AWAY_WIN, result);
    }

    @Test
    void getMatchResult_draw() {
        var match = new WCMatch();
        match.setHomeScore(1);
        match.setAwayScore(1);

        var result = MatchResult.getMatchResult(match);

        assertNotNull(result);
        assertEquals(MatchResult.DRAW, result);
    }

    @Test
    void getMatchResult_extraTimeHomeWin() {
        var match = new WCMatch();
        match.setHomeScore(2);
        match.setAwayScore(2);
        match.setExtraTime(true);
        match.setExtraTimeHomeScore(2);
        match.setExtraTimeAwayScore(1);

        var result = MatchResult.getMatchResult(match);

        assertNotNull(result);
        assertEquals(MatchResult.HOME_WIN, result);
    }

    @Test
    void getMatchResult_extraTimeAwayWin() {
        var match = new WCMatch();
        match.setHomeScore(2);
        match.setAwayScore(2);
        match.setExtraTime(true);
        match.setExtraTimeHomeScore(1);
        match.setExtraTimeAwayScore(2);

        var result = MatchResult.getMatchResult(match);

        assertNotNull(result);
        assertEquals(MatchResult.AWAY_WIN, result);
    }

    @Test
    void getMatchResult_penaltiesHomeWin() {
        var penalties = new WCPenaltiesRound();
        penalties.setHomeTeamScore(5);
        penalties.setAwayTeamScore(4);

        var match = new WCMatch();
        match.setHomeScore(2);
        match.setAwayScore(2);
        match.setExtraTime(true);
        match.setExtraTimeHomeScore(2);
        match.setExtraTimeAwayScore(2);
        match.setPenalties(true);
        match.setPenaltiesRound(penalties);

        var result = MatchResult.getMatchResult(match);

        assertNotNull(result);
        assertEquals(MatchResult.HOME_WIN, result);
    }

    @Test
    void getMatchResult_penaltiesAwayWin() {
        var penalties = new WCPenaltiesRound();
        penalties.setHomeTeamScore(4);
        penalties.setAwayTeamScore(5);

        var match = new WCMatch();
        match.setHomeScore(2);
        match.setAwayScore(2);
        match.setExtraTime(true);
        match.setExtraTimeHomeScore(2);
        match.setExtraTimeAwayScore(2);
        match.setPenalties(true);
        match.setPenaltiesRound(penalties);

        var result = MatchResult.getMatchResult(match);

        assertNotNull(result);
        assertEquals(MatchResult.AWAY_WIN, result);
    }

}