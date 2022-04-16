package com.facrod.prodemundial.service.impl;

import com.facrod.prodemundial.repository.ProdeUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdeUserDetailsServiceImpl implements UserDetailsService {

    private final ProdeUserRepository prodeUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = prodeUserRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Usuario no encontrado: {}", username);
            return new UsernameNotFoundException("User '" + username + "' not found");
        });
        var role = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        return new User(user.getUsername(), user.getPassword(), Collections.singletonList(role));
    }

}
