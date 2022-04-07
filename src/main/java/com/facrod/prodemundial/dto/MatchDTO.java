package com.facrod.prodemundial.dto;

import com.facrod.prodemundial.enums.MatchResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MatchDTO {

    @NotNull(message = "El id del partido es obligatorio")
    @Positive(message = "El id del partido no puede ser negativo")
    @Max(value = 64, message = "El id del partido no puede ser mayor que 64")
    private Long id;

    @Future(message = "La fecha del partido debe ser posterior a la fecha actual")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
    private LocalDateTime startTime;

    @NotBlank(message = "Debe ingresar el id del equipo local")
    @Pattern(regexp = "[A-Z]{3}", message = "El id del equipo local debe tener 3 letras mayúsculas")
    private String homeTeamId;
    private MatchDTO.TeamDTO homeTeam;

    @NotBlank(message = "Debe ingresar el id del equipo visitante")
    @Pattern(regexp = "[A-Z]{3}", message = "El id del equipo visitante debe tener 3 letras mayúsculas")
    private String awayTeamId;
    private MatchDTO.TeamDTO awayTeam;

    private MatchResult result;
    private boolean finished;
    private int homeScore;
    private int awayScore;
    private boolean extraTime;
    private int extraTimeHomeScore;
    private int extraTimeAwayScore;
    private boolean penalties;
    private PenaltiesRoundDTO penaltiesRound;

    @Getter
    @Builder
    public static class TeamDTO {
        private String id;
        private String name;
    }

}
