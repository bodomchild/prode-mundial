package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.SignInDTO;
import com.facrod.prodemundial.dto.TokenDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.ProdeUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthenticationControllerTest {

    @Mock
    private ProdeUserService service;

    private AuthenticationController controller;

    @BeforeEach
    void setUp() {
        openMocks(this);
        controller = new AuthenticationController(service);
    }

    @Test
    void signIn_ok() throws AppException {
        var dto = new SignInDTO();
        dto.setUsername("user");
        dto.setPassword("pass");

        when(service.signIn(dto)).thenReturn("token");

        var expectedToken = TokenDTO.builder().token("token").build();
        var expected = ResponseEntity.ok(expectedToken);

        var actual = controller.signIn(dto);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected.getBody().getToken(), actual.getBody().getToken());
    }

    @Test
    void signIn_unauthorized() throws AppException {
        var dto = new SignInDTO();
        dto.setUsername("user");
        dto.setPassword("pass");

        var expected = new AppException(HttpStatus.UNAUTHORIZED, "Error al autenticar usuario");

        when(service.signIn(dto)).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.signIn(dto));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void signUp() {
    }

    @Test
    void signUpAdmin() {
    }

}