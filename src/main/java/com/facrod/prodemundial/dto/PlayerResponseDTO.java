package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlayerResponseDTO {

    private Integer id;
    private String teamId;
    private String name;
    private int age;
    private int goals;
    private int yellowCards;
    private int redCards;

}
