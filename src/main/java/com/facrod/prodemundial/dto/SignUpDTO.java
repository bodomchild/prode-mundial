package com.facrod.prodemundial.dto;

import com.facrod.prodemundial.annotation.MatchingPasswords;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@MatchingPasswords
public class SignUpDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotNull(message = "Debe ingresar el username")
    @Pattern(regexp = "^[a-zA-Z\\d]{4,16}$", message = "El nombre de usuario debe tener entre 4 y 16 caracteres alfanuméricos")
    private String username;

    @NotNull(message = "Debe ingresar el nombre")
    @Pattern(regexp = "^[A-ZÁÉÍÓÚ][a-zA-ZáéíóúÁÉÍÓÚ ]{4,30}$", message = "El nombre debe tener entre 4 y 30 caracteres y empezar por mayúscula")
    private String name;

    @NotNull(message = "Debe ingresar el email")
    @Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "El email debe ser válido")
    private String email;

    @NotNull(message = "Debe ingresar la contraseña")
    @Pattern(regexp = "^[a-zA-Z\\d$.?!#]*$", message = "La contraseña solo puede contener letras, números y los caracteres especiales $.?!#")
    private String password;

    @NotNull(message = "Debe ingresar la confirmación de contraseña")
    @Pattern(regexp = "^[a-zA-Z\\d$.?!#]*$", message = "La contraseña solo puede contener letras, números y los caracteres especiales $.?!#")
    private String confirmPassword;

}
