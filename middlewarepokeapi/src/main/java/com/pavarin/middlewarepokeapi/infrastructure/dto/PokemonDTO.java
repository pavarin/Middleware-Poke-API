package com.pavarin.middlewarepokeapi.infrastructure.dto;

import java.util.List;

import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;

public record PokemonDTO(String name, List<Ability> abilities) {
}
