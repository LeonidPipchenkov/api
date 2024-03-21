package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.AuthResponse;
import com.pokemonreview.api.dto.LoginDto;
import com.pokemonreview.api.dto.RegisterDto;
import com.pokemonreview.api.exception.UsernameAlreadyTakenException;
import com.pokemonreview.api.model.Role;
import com.pokemonreview.api.model.UserEntity;
import com.pokemonreview.api.security.JwtHandler;
import com.pokemonreview.api.service.AuthService;
import com.pokemonreview.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtHandler jwtHandler;

    @Autowired
    public AuthServiceImpl(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                           JwtHandler jwtHandler) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtHandler = jwtHandler;
    }

    @Override
    public void register(RegisterDto registerDto) {
        if (userService.existsByUsername(registerDto.getUsername())) {
            throw new UsernameAlreadyTakenException("Username already taken");
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role role = userService.findRoleByName("USER").orElse(null);
        user.setRoles(Collections.singletonList(role));
        userService.save(user);
    }

    @Override
    public AuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtHandler.generateToken(authentication);
        return new AuthResponse(token);
    }

}
