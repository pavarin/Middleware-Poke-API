package com.pavarin.middlewarepokeapi.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.pavarin.middlewarepokeapi.application.api.PokeAPI;
import com.pavarin.middlewarepokeapi.application.usecase.GetPokemonAbilitiesUseCase;

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
    public GetPokemonAbilitiesUseCase getPokemonAbilitiesUseCase(PokeAPI pokeAPI) {
        return new GetPokemonAbilitiesUseCase(pokeAPI);
    }

}
