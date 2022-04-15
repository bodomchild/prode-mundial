package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PenaltiesRoundDTO {

    @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
    private int homeTeamScore;

    @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
    private int awayTeamScore;

    private boolean homeTeamStarted;

    @NotNull
    @Size(min = 3, message = "El equipo local debe haber pateado al menos 3 penales")
    private List<@Valid @NotNull Penalty> homeTeamPenalties;

    @NotNull
    @Size(min = 3, message = "El equipo visitante debe haber pateado al menos 3 penales")
    private List<@Valid @NotNull Penalty> awayTeamPenalties;

    @Getter
    @Setter
    public static class Penalty {
        @PositiveOrZero(message = "La cantidad de goles debe ser mayor o igual a 0")
        private int order;
        private boolean scored;
    }

}
