package com.pokemonreview.api.exception;

public class UsernameAlreadyTakenException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }

}
