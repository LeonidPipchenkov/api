package com.pokemonreview.api.exception;

public class PokemonNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public PokemonNotFoundException(String message) {
        super(message);
    }

}
