package com.facrod.prodemundial.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignInDTO {

    @NotNull(message = "Debe ingresar el username")
    @Pattern(regexp = "^[a-zA-Z\\d]{4,16}$", message = "El nombre de usuario debe tener entre 4 y 16 caracteres alfanuméricos")
    private String username;

    @NotNull(message = "Debe ingresar la contraseña")
    @Pattern(regexp = "^[a-zA-Z\\d$.?!#]*$", message = "La contraseña solo puede contener letras, números y los caracteres especiales $.?!#")
    private String password;

}
