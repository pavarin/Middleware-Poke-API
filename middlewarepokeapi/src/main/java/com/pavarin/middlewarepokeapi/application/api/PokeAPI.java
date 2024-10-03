package com.pavarin.middlewarepokeapi.application.api;

import java.util.List;

import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;

public interface PokeAPI {

    List<Ability> listAbilitiesSortedByName(String name);

}
