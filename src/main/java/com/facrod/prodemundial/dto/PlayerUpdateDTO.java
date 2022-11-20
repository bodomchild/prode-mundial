package com.facrod.prodemundial.dto;

import com.facrod.prodemundial.annotation.ValidPlayerUpdate;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ValidPlayerUpdate
public class PlayerUpdateDTO {

    @NotNull(message = "El número del jugador es obligatorio")
    @Positive(message = "El número del jugador debe ser positivo")
    @Max(value = 26, message = "El número del jugador no puede ser mayor que 26")
    private Integer id;

    @NotBlank(message = "El id del equipo es obligatorio")
    @Pattern(regexp = "[A-Z]{3}", message = "El id del equipo debe tener 3 letras mayúsculas")
    private String teamId;

    private Integer goals;
    private Integer yellowCards;
    private Integer redCards;

}
