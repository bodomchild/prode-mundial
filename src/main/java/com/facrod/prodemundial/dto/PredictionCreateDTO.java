package com.facrod.prodemundial.dto;

import com.facrod.prodemundial.annotation.ValidPrediction;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ValidPrediction
public class PredictionCreateDTO {

    @NotNull(message = "El id del partido es obligatorio")
    @Positive(message = "El id del partido no puede ser negativo")
    @Max(value = 64, message = "El id del partido no puede ser mayor que 64")
    private Long matchId;

    @PositiveOrZero(message = "La cantidad de goles del equipo local debe ser mayor o igual a 0")
    private int homeScore;

    @PositiveOrZero(message = "La cantidad de goles del equipo visitante debe ser mayor o igual a 0")
    private int awayScore;

    @Valid
    private MatchExtraDTO extraTime;

    @Valid
    private MatchExtraDTO penalties;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MatchExtraDTO {
        @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
        private int homeScore;

        @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
        private int awayScore;
    }

}
