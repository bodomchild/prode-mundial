package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.dto.SignInDTO;
import com.facrod.prodemundial.dto.SignUpDTO;
import com.facrod.prodemundial.entity.ProdeUser;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.repository.ProdeUserRepository;
import com.facrod.prodemundial.security.JWTUtil;
import com.facrod.prodemundial.service.ProdeUserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdeUserServiceImpl implements ProdeUserService {

    private static final String PASSWORD_NOT_MATCH = "Las contraseñas no coinciden";

    private final AuthenticationManager authenticationManager;
    private final Gson gson;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ProdeUserRepository prodeUserRepository;

    @Override
    public SignUpDTO signUp(SignUpDTO user) throws AppException {
        var prodeUser = checkAndSetUser(user);
        prodeUser.setRole("USER");

        try {
            log.info("Creando usuario: {}", gson.toJson(prodeUser));
            prodeUser = prodeUserRepository.save(prodeUser);
        } catch (Exception e) {
            log.error("Error al crear usuario: {}", gson.toJson(prodeUser));
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear usuario");
        }

        user.setId(prodeUser.getId());
        user.setPassword(null);
        user.setConfirmPassword(null);
        return user;
    }

    @Override
    public String signIn(SignInDTO user) throws AppException {
        try {
            log.info("Autenticando usuario: {}", user.getUsername());
            var token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            authenticationManager.authenticate(token);
            return jwtUtil.generateToken(user.getUsername());
        } catch (AppException e) {
            log.error("Error al autenticar usuario: {}", user.getUsername());
            throw e;
        } catch (Exception e) {
            log.error("Error al autenticar usuario: {}", user.getUsername());
            throw new AppException(HttpStatus.UNAUTHORIZED, "Error al autenticar usuario");
        }
    }

    @Override
    public SignUpDTO signUpAdmin(SignUpDTO user) throws AppException {
        var prodeAdmin = checkAndSetUser(user);
        prodeAdmin.setRole("ADMIN");

        try {
            log.info("Creando admin: {}", gson.toJson(prodeAdmin));
            prodeAdmin = prodeUserRepository.save(prodeAdmin);
        } catch (Exception e) {
            log.error("Error al crear admin: {}", gson.toJson(prodeAdmin));
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear admin");
        }

        user.setId(prodeAdmin.getId());
        user.setPassword(null);
        user.setConfirmPassword(null);
        return user;
    }


    private ProdeUser checkAndSetUser(SignUpDTO user) throws AppException {
        if (prodeUserRepository.existsByUsername(user.getUsername())) {
            log.error("El usuario ya existe: {}", user.getUsername());
            throw new AppException(HttpStatus.CONFLICT, "El usuario ya existe");
        }

        var prodeUser = new ProdeUser();
        prodeUser.setUsername(user.getUsername());

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            log.error(PASSWORD_NOT_MATCH);
            throw new AppException(HttpStatus.BAD_REQUEST, PASSWORD_NOT_MATCH);
        }

        prodeUser.setPassword(passwordEncoder.encode(user.getPassword()));
        prodeUser.setEmail(user.getEmail());
        prodeUser.setName(user.getName());
        return prodeUser;
    }

}
