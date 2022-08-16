package com.facrod.prodemundial.security;

import com.facrod.prodemundial.dto.ErrorDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.impl.ProdeUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final ProdeUserDetailsServiceImpl prodeUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            var token = authHeader.substring(7);

            try {
                if (token.isBlank()) {
                    throw new AppException(HttpStatus.BAD_REQUEST, "Token JWT no válido");
                } else {
                    var username = jwtUtil.getUsernameFromToken(token);
                    var userDetails = prodeUserDetailsService.loadUserByUsername(username);
                    var authToken = new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (AppException e) {
                sendErrorResponse(response, e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, AppException e) {
        var error = new ErrorDTO();
        error.setStatus(e.getStatus().getReasonPhrase());
        error.setError(e.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(e.getStatus().value());
    }

}
