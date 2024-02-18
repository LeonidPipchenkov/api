package com.pokemonreview.api.exception;

public class ReviewNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public ReviewNotFoundException(String message) {
        super(message);
    }

}
