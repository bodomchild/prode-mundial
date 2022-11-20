package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScorerDTO {

    @Positive(message = "El id del jugador no puede ser negativo")
    @Max(value = 26, message = "El id del jugador no puede ser mayor que 26")
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;

    @Positive(message = "La cantidad de goles debe ser positiva")
    private int goals;

}
