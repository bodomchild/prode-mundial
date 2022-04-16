package com.facrod.prodemundial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignUpDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private String username;
    private String name;
    private String email;
    private String password;
    private String confirmPassword;

}
