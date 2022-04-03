package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TeamDTO {

    @NotNull(message = "El id del equipo es obligatorio")
    private String id;
    @NotNull(message = "El nombre del equipo es obligatorio")
    private String name;
    private int points;
    private int goals;
    private int goalsAgainst;
    private int goalDifference;
    private int wins;
    private int draws;
    private int losses;
    private int playedGames;
    @NotNull(message = "El grupo del equipo es obligatorio")
    private String group;

}
