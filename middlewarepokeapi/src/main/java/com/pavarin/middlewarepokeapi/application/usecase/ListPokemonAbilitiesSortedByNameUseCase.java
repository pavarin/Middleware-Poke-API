package com.pavarin.middlewarepokeapi.application.usecase;

import com.pavarin.middlewarepokeapi.application.UseCaseReactive;
import com.pavarin.middlewarepokeapi.application.api.PokeAPI;
import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

public class ListPokemonAbilitiesSortedByNameUseCase
        implements UseCaseReactive<ListPokemonAbilitiesSortedByNameUseCase.Input, ListPokemonAbilitiesSortedByNameUseCase.Output> {

    private final PokeAPI<String, Mono<List<Ability>>> pokeAPI;

    public ListPokemonAbilitiesSortedByNameUseCase(final PokeAPI<String, Mono<List<Ability>>> pokeAPI) {
        this.pokeAPI = pokeAPI;
    }

    @Override
    public Mono<Output> execute(Input input) {
        return pokeAPI
                .listAbilities(input.name())
                .map(abilities -> new Output(
                        abilities.stream()
                                .sorted(Comparator.comparing(ability -> ability.getAbilityDetail().getName()))
                                .toList()));
    }

    public record Input(String name) {}

    public record Output(List<Ability> abilities) {}

}
