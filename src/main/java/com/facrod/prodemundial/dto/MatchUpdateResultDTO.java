package com.facrod.prodemundial.dto;

import com.facrod.prodemundial.annotation.ValidExtraTime;
import com.facrod.prodemundial.annotation.ValidPenaltiesRound;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ValidExtraTime
@ValidPenaltiesRound
public class MatchUpdateResultDTO {

    @NotNull(message = "El id del partido es obligatorio")
    @Positive(message = "El id del partido no puede ser negativo")
    @Max(value = 64, message = "El id del partido no puede ser mayor que 64")
    private Long id;

    @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
    private int homeScore;

    @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
    private int awayScore;

    private Boolean extraTime;

    @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
    private Integer extraTimeHomeScore;

    @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
    private Integer extraTimeAwayScore;

    private Boolean penalties;

    @Valid
    private PenaltiesRoundDTO penaltiesRound;

}
