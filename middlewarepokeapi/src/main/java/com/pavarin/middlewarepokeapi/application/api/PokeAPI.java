package com.pavarin.middlewarepokeapi.application.api;

public interface PokeAPI<T, R> {

    R listAbilities(T name);

}
