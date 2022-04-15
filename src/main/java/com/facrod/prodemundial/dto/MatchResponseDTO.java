package com.facrod.prodemundial.dto;

import com.facrod.prodemundial.enums.MatchResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MatchResponseDTO {

    private Long id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime startTime;

    private MatchResponseDTO.TeamDTO homeTeam;
    private MatchResponseDTO.TeamDTO awayTeam;
    private MatchResult result;
    private boolean finished;
    private Integer homeScore;
    private Integer awayScore;
    private Boolean extraTime;
    private Integer extraTimeHomeScore;
    private Integer extraTimeAwayScore;
    private Boolean penalties;
    private PenaltiesRoundDTO penaltiesRound;

    @Getter
    @Builder
    public static class TeamDTO {
        private String id;
        private String name;
    }

}
