package com.pokemonreview.api.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String tokenType = "Bearer ";
    private String accessToken;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
