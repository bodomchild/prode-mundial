package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.SignInDTO;
import com.facrod.prodemundial.dto.SignUpDTO;
import com.facrod.prodemundial.entity.ProdeUser;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.repository.ProdeUserRepository;
import com.facrod.prodemundial.security.JWTUtil;
import com.facrod.prodemundial.service.ProdeUserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ProdeUserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ProdeUserRepository prodeUserRepository;

    private ProdeUserService prodeUserService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        prodeUserService = new ProdeUserServiceImpl(authenticationManager, new Gson(), jwtUtil, passwordEncoder, prodeUserRepository);
    }

    @Test
    void signUp_ok() throws AppException {
        var request = new SignUpDTO();
        request.setUsername("test");
        request.setName("Test User");
        request.setEmail("test@mail.com");
        request.setPassword("password");
        request.setConfirmPassword("password");
        var savedUser = new ProdeUser();
        savedUser.setId(UUID.randomUUID().toString());
        savedUser.setUsername(request.getUsername());
        savedUser.setName(request.getName());
        savedUser.setEmail(request.getEmail());
        var expected = new SignUpDTO();
        expected.setId(savedUser.getId());
        expected.setUsername(savedUser.getUsername());
        expected.setName(savedUser.getName());
        expected.setEmail(savedUser.getEmail());

        when(prodeUserRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(prodeUserRepository.save(any(ProdeUser.class))).thenReturn(savedUser);

        var actual = prodeUserService.signUp(request);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void signUp_userAlreadyExists() {
        var request = new SignUpDTO();
        request.setUsername("test");
        request.setName("Test User");
        request.setEmail("test@mail.com");
        request.setPassword("password");
        request.setConfirmPassword("password");
        var expected = new AppException(HttpStatus.CONFLICT, "El usuario ya existe");

        when(prodeUserRepository.existsByUsername(request.getUsername())).thenReturn(true);

        var actual = assertThrows(AppException.class, () -> prodeUserService.signUp(request));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void signUp_passwordMismatch() {
        var request = new SignUpDTO();
        request.setUsername("test");
        request.setName("Test User");
        request.setEmail("test@mail.com");
        request.setPassword("password");
        request.setConfirmPassword("password2");
        var expected = new AppException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");

        when(prodeUserRepository.existsByUsername(request.getUsername())).thenReturn(false);

        var actual = assertThrows(AppException.class, () -> prodeUserService.signUp(request));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void signUp_saveException() {
        var request = new SignUpDTO();
        request.setUsername("test");
        request.setName("Test User");
        request.setEmail("test@mail.com");
        request.setPassword("password");
        request.setConfirmPassword("password");
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear usuario");

        when(prodeUserRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        doThrow(new RuntimeException()).when(prodeUserRepository).save(any(ProdeUser.class));

        var actual = assertThrows(AppException.class, () -> prodeUserService.signUp(request));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void signIn_ok() throws AppException {
        var request = new SignInDTO();
        request.setUsername("test");
        request.setPassword("password");
        var expected = "token";

        when(jwtUtil.generateToken("test")).thenReturn(expected);

        var actual = prodeUserService.signIn(request);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void signIn_authenticationException() {
        var request = new SignInDTO();
        request.setUsername("test");
        request.setPassword("password");
        var exception = mock(AuthenticationException.class);
        var expected = new AppException(HttpStatus.UNAUTHORIZED, "Error al autenticar usuario");

        doThrow(exception).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        var actual = assertThrows(AppException.class, () -> prodeUserService.signIn(request));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void signIn_tokenGenerationException() throws AppException {
        var request = new SignInDTO();
        request.setUsername("test");
        request.setPassword("password");
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar el token");

        when(jwtUtil.generateToken("test")).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> prodeUserService.signIn(request));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void signUpAdmin_ok() throws AppException {
        var request = new SignUpDTO();
        request.setUsername("admin");
        request.setName("Test Admin");
        request.setEmail("admin@mail.com");
        request.setPassword("password");
        request.setConfirmPassword("password");
        var savedUser = new ProdeUser();
        savedUser.setId(UUID.randomUUID().toString());
        savedUser.setUsername(request.getUsername());
        savedUser.setName(request.getName());
        savedUser.setEmail(request.getEmail());
        var expected = new SignUpDTO();
        expected.setId(savedUser.getId());
        expected.setUsername(savedUser.getUsername());
        expected.setName(savedUser.getName());
        expected.setEmail(savedUser.getEmail());

        when(prodeUserRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(prodeUserRepository.save(any(ProdeUser.class))).thenReturn(savedUser);

        var actual = prodeUserService.signUpAdmin(request);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void signUpAdmin_userAlreadyExists() {
        var request = new SignUpDTO();
        request.setUsername("test");
        request.setName("Test User");
        request.setEmail("test@mail.com");
        request.setPassword("password");
        request.setConfirmPassword("password");
        var expected = new AppException(HttpStatus.CONFLICT, "El usuario ya existe");

        when(prodeUserRepository.existsByUsername(request.getUsername())).thenReturn(true);

        var actual = assertThrows(AppException.class, () -> prodeUserService.signUpAdmin(request));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void signUpAdmin_passwordMismatch() {
        var request = new SignUpDTO();
        request.setUsername("test");
        request.setName("Test User");
        request.setEmail("test@mail.com");
        request.setPassword("password");
        request.setConfirmPassword("password2");
        var expected = new AppException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");

        when(prodeUserRepository.existsByUsername(request.getUsername())).thenReturn(false);

        var actual = assertThrows(AppException.class, () -> prodeUserService.signUpAdmin(request));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    void signUpAdmin_saveException() {
        var request = new SignUpDTO();
        request.setUsername("test");
        request.setName("Test User");
        request.setEmail("test@mail.com");
        request.setPassword("password");
        request.setConfirmPassword("password");
        var expected = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear admin");

        when(prodeUserRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        doThrow(new RuntimeException()).when(prodeUserRepository).save(any(ProdeUser.class));

        var actual = assertThrows(AppException.class, () -> prodeUserService.signUpAdmin(request));

        assertNotNull(actual);
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getMessage(), actual.getMessage());
    }

}