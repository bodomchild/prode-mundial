package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MatchPredictionResponseDTO {

    private String username;
    private Long matchId;
    private String winner;
    private int homeScore;
    private int awayScore;
    private MatchExtraDTO extraTime;
    private MatchExtraDTO penalties;
    private List<ScorerDTO> homeTeamScorers;
    private List<ScorerDTO> awayTeamScorers;

}
