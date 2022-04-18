package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.SignInDTO;
import com.facrod.prodemundial.dto.SignUpDTO;
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
        var requestBody = new SignInDTO();
        requestBody.setUsername("user");
        requestBody.setPassword("pass");

        var expectedToken = TokenDTO.builder().token("token").build();
        var expected = ResponseEntity.ok(expectedToken);

        when(service.signIn(requestBody)).thenReturn("token");

        var actual = controller.signIn(requestBody);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected.getBody(), actual.getBody());
    }

    @Test
    void signIn_error() throws AppException {
        var requestBody = new SignInDTO();
        requestBody.setUsername("user");
        requestBody.setPassword("pass");

        var expected = new AppException(HttpStatus.UNAUTHORIZED, "Error al autenticar usuario");

        when(service.signIn(requestBody)).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.signIn(requestBody));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void signUp_ok() throws AppException {
        var requestBody = createSignUpDTO();
        var responseBody = createSignUpDTO();
        responseBody.setId("id");
        responseBody.setPassword(null);
        responseBody.setConfirmPassword(null);

        var expected = ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

        when(service.signUp(requestBody)).thenReturn(responseBody);

        var actual = controller.signUp(requestBody);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected.getBody(), actual.getBody());
    }

    @Test
    void signUp_error() throws AppException {
        var requestBody = createSignUpDTO();
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear usuario");

        when(service.signUp(requestBody)).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.signUp(requestBody));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void signUpAdmin() throws AppException {
        var requestBody = createSignUpDTO();
        var responseBody = createSignUpDTO();
        responseBody.setId("id");
        responseBody.setPassword(null);
        responseBody.setConfirmPassword(null);

        var expected = ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

        when(service.signUpAdmin(requestBody)).thenReturn(responseBody);

        var actual = controller.signUpAdmin(requestBody);

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected.getBody(), actual.getBody());
    }

    @Test
    void signUpAdmin_error() throws AppException {
        var requestBody = createSignUpDTO();
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear admin");

        when(service.signUpAdmin(requestBody)).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.signUpAdmin(requestBody));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private SignUpDTO createSignUpDTO() {
        var dto = new SignUpDTO();
        dto.setUsername("user");
        dto.setEmail("email");
        dto.setName("name");
        dto.setPassword("pass");
        dto.setConfirmPassword("pass");
        return dto;
    }

}