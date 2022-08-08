package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlayerCreateDTO {

    @NotNull(message = "El número del jugador es obligatorio")
    @Positive(message = "El número del jugador debe ser positivo")
    private Integer id;

    @NotBlank(message = "El id del equipo es obligatorio")
    @Pattern(regexp = "[A-Z]{3}", message = "El id del equipo debe tener 3 letras mayúsculas")
    private String teamId;

    @NotBlank(message = "El nombre del jugador es obligatorio")
    private String name;

    @NotNull(message = "La edad del jugador es obligatoria")
    @Positive(message = "La edad del jugador debe ser positiva")
    private int age;

}
