package com.pokemonreview.api.controller;

import com.pokemonreview.api.dto.AuthResponse;
import com.pokemonreview.api.dto.LoginDto;
import com.pokemonreview.api.dto.RegisterDto;
import com.pokemonreview.api.model.Role;
import com.pokemonreview.api.model.UserEntity;
import com.pokemonreview.api.repository.RoleRepository;
import com.pokemonreview.api.repository.UserRepository;
import com.pokemonreview.api.security.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class AuthController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtHandler jwtHandler;

    @Autowired
    public AuthController(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder, JwtHandler jwtHandler) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtHandler = jwtHandler;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username already taken!", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role role = roleRepository.findByName("USER").orElse(null);
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtHandler.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

}
