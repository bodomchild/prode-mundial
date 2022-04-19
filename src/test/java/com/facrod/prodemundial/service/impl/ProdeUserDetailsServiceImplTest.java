package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.entity.ProdeUser;
import com.facrod.prodemundial.repository.ProdeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ProdeUserDetailsServiceImplTest {

    @Mock
    private ProdeUserRepository prodeUserRepository;

    private ProdeUserDetailsServiceImpl prodeUserDetailsService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        prodeUserDetailsService = new ProdeUserDetailsServiceImpl(prodeUserRepository);
    }

    @Test
    void loadUserByUsername_ok() {
        var user = new ProdeUser();
        user.setUsername("user");
        user.setPassword("password");
        user.setRole("USER");
        var expected = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(prodeUserRepository.findByUsername("user")).thenReturn(Optional.of(user));

        var actual = prodeUserDetailsService.loadUserByUsername("user");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void loadUserByUsername_notFound() {
        var user = new ProdeUser();
        user.setUsername("user");
        user.setPassword("password");
        user.setRole("USER");
        var expected = new UsernameNotFoundException("User 'user' not found");

        when(prodeUserRepository.findByUsername("user")).thenReturn(Optional.empty());

        var actual = assertThrows(UsernameNotFoundException.class, () -> prodeUserDetailsService.loadUserByUsername("user"));

        assertNotNull(actual);
        assertEquals(expected.getMessage(), actual.getMessage());
    }

}