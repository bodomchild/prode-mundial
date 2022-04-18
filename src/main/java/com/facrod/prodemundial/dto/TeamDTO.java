package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamDTO {

    @NotBlank(message = "El id del equipo es obligatorio")
    @Pattern(regexp = "[A-Z]{3}", message = "El id del equipo debe tener 3 letras mayúsculas")
    private String id;
    @NotBlank(message = "El nombre del equipo es obligatorio")
    @Pattern(regexp = "^[A-Z][A-Za-z\\s]*$", message = "El nombre del equipo debe empezar con mayúscula")
    private String name;
    private int points;
    private int goals;
    private int goalsAgainst;
    private int goalDifference;
    private int wins;
    private int draws;
    private int losses;
    private int playedGames;
    @NotBlank(message = "El grupo del equipo es obligatorio")
    @Pattern(regexp = "[A-Z]", message = "El grupo del equipo debe tener 1 letra mayúscula")
    private String group;

}
