package com.facrod.prodemundial.mapper;

import com.facrod.prodemundial.dto.PenaltiesRoundDTO;
import com.facrod.prodemundial.entity.WCPenaltiesRound;
import com.facrod.prodemundial.entity.WCPenalty;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PenaltiesRoundMapperTest {

    @Test
    void toDto() {
        var penaltiesRound = createPenaltiesRoundEntity();
        var expected = createPenaltiesRoundDto();

        var actual = PenaltiesRoundMapper.toDto(penaltiesRound);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void toEntity() {
        var penaltiesRound = createPenaltiesRoundDto();
        var expected = createPenaltiesRoundEntity();

        var actual = PenaltiesRoundMapper.toEntity(penaltiesRound);

        assertNotNull(actual);
        assertEquals(expected.isHomeTeamStarted(), actual.isHomeTeamStarted());
        assertEquals(expected.getHomeTeamScore(), actual.getHomeTeamScore());
        assertEquals(expected.getAwayTeamScore(), actual.getAwayTeamScore());
        assertEquals(expected.getHomeTeamPenalties().size(), actual.getHomeTeamPenalties().size());
        assertEquals(expected.getAwayTeamPenalties().size(), actual.getAwayTeamPenalties().size());

        for (int i = 0; i < expected.getHomeTeamPenalties().size(); i++) {
            assertEquals(expected.getHomeTeamPenalties().get(i).getOrder(), actual.getHomeTeamPenalties().get(i).getOrder());
            assertEquals(expected.getHomeTeamPenalties().get(i).isScored(), actual.getHomeTeamPenalties().get(i).isScored());
        }

        for (int i = 0; i < expected.getAwayTeamPenalties().size(); i++) {
            assertEquals(expected.getAwayTeamPenalties().get(i).getOrder(), actual.getAwayTeamPenalties().get(i).getOrder());
            assertEquals(expected.getAwayTeamPenalties().get(i).isScored(), actual.getAwayTeamPenalties().get(i).isScored());
        }
    }

    private PenaltiesRoundDTO createPenaltiesRoundDto() {
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

        return penaltiesRound;
    }

    private WCPenaltiesRound createPenaltiesRoundEntity() {
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

        return penaltiesRound;
    }

}