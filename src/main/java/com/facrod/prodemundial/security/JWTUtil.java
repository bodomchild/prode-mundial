package com.facrod.prodemundial.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.facrod.prodemundial.exceptions.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret:secret}")
    private String secret;

    @Value("${jwt.expiration:3600000}")
    private int expiration;

    @Value("${jwt.issuer}")
    private String issuer;

    public String generateToken(String username) throws AppException {
        try {
            var iat = new Date();
            var exp = new Date(iat.getTime() + expiration);
            return JWT.create()
                    .withSubject(username)
                    .withIssuer(issuer)
                    .withIssuedAt(iat)
                    .withExpiresAt(exp)
                    .sign(Algorithm.HMAC256(secret));
        } catch (IllegalArgumentException | JWTCreationException e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar el token");
        }
    }

    public String getUsernameFromToken(String token) throws AppException {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (IllegalArgumentException | JWTVerificationException e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Token JWT no válido");
        }
    }

}
