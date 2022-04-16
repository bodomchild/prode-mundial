package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.SignInDTO;
import com.facrod.prodemundial.dto.SignUpDTO;
import com.facrod.prodemundial.dto.TokenDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.ProdeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final ProdeUserService prodeUserService;

    // TODO: 15/4/22 agregar validaciones a los dtos

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDTO> signIn(@RequestBody @Valid SignInDTO signInCredentials) throws AppException {
        var token = TokenDTO.builder().token(prodeUserService.signIn(signInCredentials)).build();
        return ResponseEntity.ok(token);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpDTO> signUp(@RequestBody @Valid SignUpDTO signUpCredentials) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(prodeUserService.signUp(signUpCredentials));
    }

    @PostMapping("/sign-up/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SignUpDTO> signUpAdmin(@RequestBody @Valid SignUpDTO signUpCredentials) throws AppException {
        return ResponseEntity.status(HttpStatus.CREATED).body(prodeUserService.signUpAdmin(signUpCredentials));
    }

}
