package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.MatchCreateDTO;
import com.facrod.prodemundial.dto.MatchResponseDTO;
import com.facrod.prodemundial.dto.PenaltiesRoundDTO;
import com.facrod.prodemundial.entity.WCMatch;
import com.facrod.prodemundial.entity.WCPenaltiesRound;
import com.facrod.prodemundial.entity.WCPenalty;
import com.facrod.prodemundial.entity.WCTeam;
import com.facrod.prodemundial.enums.MatchResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MatchMapperTest {

    @Test
    void toMatchResponseDto_notFinished() {
        var match = createNotFinishedMatch();
        var expected = createNotFinishedMatchResponse();

        var actual = MatchMapper.toMatchResponseDto(match);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toMatchResponseDto_finished() {
        var match = createFinishedMatch();
        var expected = createFinishedMatchResponse();

        var actual = MatchMapper.toMatchResponseDto(match);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toMatchResponseDto_extraTime() {
        var match = createFinishedMatchExtraTime();
        var expected = createFinishedMatchResponseExtraTime();

        var actual = MatchMapper.toMatchResponseDto(match);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toMatchResponseDto_penalties() {
        var match = createFinishedMatchPenalties();
        var expected = createFinishedMatchResponsePenalties();

        var actual = MatchMapper.toMatchResponseDto(match);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void matchCreateDtoToEntity() {
        var matchCreate = new MatchCreateDTO();
        matchCreate.setId(8L);
        matchCreate.setHomeTeamId("ARG");
        matchCreate.setAwayTeamId("MEX");
        matchCreate.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        var expected = createNotFinishedMatch();

        var actual = MatchMapper.matchCreateDtoToEntity(matchCreate);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toMatchResponseDtoList() {
        var match = List.of(createNotFinishedMatch(), createFinishedMatch());
        var expected = List.of(createNotFinishedMatchResponse(), createFinishedMatchResponse());

        var actual = MatchMapper.toMatchResponseDto(match);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private WCMatch createNotFinishedMatch() {
        var homeTeam = new WCTeam();
        homeTeam.setId("ARG");
        homeTeam.setName("Argentina");

        var awayTeam = new WCTeam();
        awayTeam.setId("MEX");
        awayTeam.setName("Mexico");

        var match = new WCMatch();
        match.setId(8L);
        match.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setFinished(false);

        return match;
    }

    private WCMatch createFinishedMatch() {
        var match = createNotFinishedMatch();
        match.setFinished(true);
        match.setHomeScore(3);
        match.setAwayScore(1);
        return match;
    }

    private WCMatch createFinishedMatchExtraTime() {
        var match = createFinishedMatch();
        match.setAwayScore(3);
        match.setExtraTime(true);
        match.setExtraTimeHomeScore(1);
        match.setExtraTimeAwayScore(0);
        return match;
    }

    private WCMatch createFinishedMatchPenalties() {
        var p1 = new WCPenalty();
        p1.setOrder(1);
        p1.setScored(true);

        var p2 = new WCPenalty();
        p2.setOrder(2);
        p2.setScored(true);

        var p3 = new WCPenalty();
        p3.setOrder(3);
        p3.setScored(true);

        var p4 = new WCPenalty();
        p4.setOrder(4);
        p4.setScored(true);

        var p5 = new WCPenalty();
        p5.setOrder(5);
        p5.setScored(true);

        var p6 = new WCPenalty();
        p6.setOrder(6);
        p6.setScored(true);

        var p7 = new WCPenalty();
        p7.setOrder(7);
        p7.setScored(true);

        var p8 = new WCPenalty();
        p8.setOrder(8);
        p8.setScored(false);

        var p9 = new WCPenalty();
        p9.setOrder(9);
        p9.setScored(true);

        var penaltiesRound = new WCPenaltiesRound();
        penaltiesRound.setHomeTeamStarted(true);
        penaltiesRound.setHomeTeamScore(5);
        penaltiesRound.setAwayTeamScore(3);
        penaltiesRound.setHomeTeamPenalties(List.of(p1, p3, p5, p7, p9));
        penaltiesRound.setAwayTeamPenalties(List.of(p2, p4, p6, p8));

        var match = createFinishedMatchExtraTime();
        match.setExtraTimeAwayScore(1);
        match.setPenalties(true);
        match.setPenaltiesRound(penaltiesRound);

        return match;
    }

    private MatchResponseDTO createNotFinishedMatchResponse() {
        var dto = new MatchResponseDTO();
        dto.setId(8L);
        dto.setStartTime(LocalDateTime.of(2022, 11, 22, 7, 0, 0));
        dto.setHomeTeam(MatchResponseDTO.TeamDTO.builder().id("ARG").name("Argentina").build());
        dto.setAwayTeam(MatchResponseDTO.TeamDTO.builder().id("MEX").name("Mexico").build());
        dto.setFinished(false);
        return dto;
    }

    private MatchResponseDTO createFinishedMatchResponse() {
        var dto = createNotFinishedMatchResponse();
        dto.setFinished(true);
        dto.setHomeScore(3);
        dto.setAwayScore(1);
        dto.setResult(MatchResult.HOME_WIN);
        return dto;
    }

    private MatchResponseDTO createFinishedMatchResponseExtraTime() {
        var dto = createFinishedMatchResponse();
        dto.setAwayScore(3);
        dto.setExtraTime(true);
        dto.setExtraTimeHomeScore(1);
        dto.setExtraTimeAwayScore(0);
        return dto;
    }

    private MatchResponseDTO createFinishedMatchResponsePenalties() {
        var p1 = new PenaltiesRoundDTO.Penalty();
        p1.setOrder(1);
        p1.setScored(true);

        var p2 = new PenaltiesRoundDTO.Penalty();
        p2.setOrder(2);
        p2.setScored(true);

        var p3 = new PenaltiesRoundDTO.Penalty();
        p3.setOrder(3);
        p3.setScored(true);

        var p4 = new PenaltiesRoundDTO.Penalty();
        p4.setOrder(4);
        p4.setScored(true);

        var p5 = new PenaltiesRoundDTO.Penalty();
        p5.setOrder(5);
        p5.setScored(true);

        var p6 = new PenaltiesRoundDTO.Penalty();
        p6.setOrder(6);
        p6.setScored(true);

        var p7 = new PenaltiesRoundDTO.Penalty();
        p7.setOrder(7);
        p7.setScored(true);

        var p8 = new PenaltiesRoundDTO.Penalty();
        p8.setOrder(8);
        p8.setScored(false);

        var p9 = new PenaltiesRoundDTO.Penalty();
        p9.setOrder(9);
        p9.setScored(true);

        var penaltiesRound = new PenaltiesRoundDTO();
        penaltiesRound.setHomeTeamStarted(true);
        penaltiesRound.setHomeTeamScore(5);
        penaltiesRound.setAwayTeamScore(3);
        penaltiesRound.setHomeTeamPenalties(List.of(p1, p3, p5, p7, p9));
        penaltiesRound.setAwayTeamPenalties(List.of(p2, p4, p6, p8));

        var dto = createFinishedMatchResponseExtraTime();
        dto.setExtraTimeAwayScore(1);
        dto.setPenalties(true);
        dto.setPenaltiesRound(penaltiesRound);
        return dto;
    }

}