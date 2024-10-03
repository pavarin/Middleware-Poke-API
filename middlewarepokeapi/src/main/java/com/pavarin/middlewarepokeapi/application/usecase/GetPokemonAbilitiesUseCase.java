package com.pavarin.middlewarepokeapi.application.usecase;

import java.util.List;

import com.pavarin.middlewarepokeapi.application.UseCase;
import com.pavarin.middlewarepokeapi.application.api.PokeAPI;
import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;

public class GetPokemonAbilitiesUseCase
        implements UseCase<GetPokemonAbilitiesUseCase.Input, GetPokemonAbilitiesUseCase.Output> {

    private final PokeAPI pokeAPI;

    public GetPokemonAbilitiesUseCase(final PokeAPI pokeAPI) {
        this.pokeAPI = pokeAPI;
    }

    @Override
    public Output execute(Input input) {
        return new Output(pokeAPI
                .listAbilitiesSortedByName(input.name()));
    }

    public record Input(String name) {}

    public record Output(List<Ability> abilities) {}

}
