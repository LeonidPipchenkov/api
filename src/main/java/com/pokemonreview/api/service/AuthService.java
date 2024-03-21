package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.AuthResponse;
import com.pokemonreview.api.dto.LoginDto;
import com.pokemonreview.api.dto.RegisterDto;

public interface AuthService {
    void register(RegisterDto registerDto);
    AuthResponse login(LoginDto loginDto);
}
