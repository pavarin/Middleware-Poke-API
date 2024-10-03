package com.pavarin.middlewarepokeapi.application.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String name) {
        super("Pokémon with name '" + name + "' not found.");
    }
}
