package com.pavarin.middlewarepokeapi.infrastructure.configuration;

import com.pavarin.middlewarepokeapi.application.api.PokeAPI;
import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;
import com.pavarin.middlewarepokeapi.application.usecase.ListPokemonAbilitiesSortedByNameUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class PokeApiConfig {

    @Value("${pokeapi.base-url}")
    private String pokeApiBaseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(pokeApiBaseUrl)
                .build();
    }

    @Bean
    public ListPokemonAbilitiesSortedByNameUseCase getPokemonAbilitiesUseCase(PokeAPI<String, Mono<List<Ability>>> pokeAPI) {
        return new ListPokemonAbilitiesSortedByNameUseCase(pokeAPI);
    }

}
