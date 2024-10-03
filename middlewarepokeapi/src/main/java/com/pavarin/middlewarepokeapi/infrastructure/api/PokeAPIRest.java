package com.pavarin.middlewarepokeapi.infrastructure.api;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.pavarin.middlewarepokeapi.application.api.PokeAPI;
import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;
import com.pavarin.middlewarepokeapi.infrastructure.dto.PokemonDTO;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
public class PokeAPIRest implements PokeAPI {

    private final WebClient webClient;

    public PokeAPIRest(final WebClient webClient) {
        this.webClient = Objects.requireNonNull(webClient);
    }

    @Override
    public List<Ability> listAbilitiesSortedByName(String name) {

        var pokemonDTO = webClient.get()
                .uri("/pokemon/{name}", name.toLowerCase())
                .retrieve()
                .bodyToMono(PokemonResponse.class)
                .map(this::mapToDTO)
                .block();

        return pokemonDTO
                .abilities()
                .stream()
                .sorted(Comparator.comparing(ability ->
                        ability.getAbilityDetail().getName()))
                .toList();
    }

    private PokemonDTO mapToDTO(PokemonResponse pokemonResponse) {
        return new PokemonDTO(pokemonResponse.name(),
                pokemonResponse.abilities());
    }

    public record PokemonResponse(String name, List<Ability> abilities) {

        public PokemonResponse {
            Objects.requireNonNull(name);
            Objects.requireNonNull(abilities);
        }

    }

}
