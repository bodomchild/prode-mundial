package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MatchExtraDTO {

    @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
    private int homeScore;

    @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
    private int awayScore;

}
