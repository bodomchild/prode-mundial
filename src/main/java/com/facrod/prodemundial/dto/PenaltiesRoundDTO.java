package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PenaltiesRoundDTO {

    private int homeTeamScore;
    private int awayTeamScore;
    private boolean homeTeamStarted;
    private List<Penalty> homeTeamPenalties;
    private List<Penalty> awayTeamPenalties;


    @Getter
    @Setter
    public static class Penalty {
        private int order;
        private boolean scored;
    }

}
