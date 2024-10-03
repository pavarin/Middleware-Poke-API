package com.pavarin.middlewarepokeapi.infrastructure.rest;

import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;
import com.pavarin.middlewarepokeapi.application.exception.PokemonNotFoundException;
import com.pavarin.middlewarepokeapi.application.usecase.ListPokemonAbilitiesSortedByNameUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(PokeApiController.class)
public class PokeApiControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ListPokemonAbilitiesSortedByNameUseCase listPokemonAbilitiesSortedByNameUseCase;

    @Test
    public void testListAbilitiesSuccess() {
        // Arrange
        String pokemonName = "pikachu";
        List<Ability> abilities = List.of(
                new Ability(new Ability.AbilityDetail("static", ""),
                        false,
                        1),
                new Ability(new Ability.AbilityDetail("lightning-rod", ""),
                        false,
                        2));
        ListPokemonAbilitiesSortedByNameUseCase.Output output = new ListPokemonAbilitiesSortedByNameUseCase.Output(abilities);

        when(listPokemonAbilitiesSortedByNameUseCase.execute(any())).thenReturn(Mono.just(output));

        // Act & Assert
        webTestClient.get()
                .uri("/v1/pokemon/{name}/abilities", pokemonName)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.abilities").isArray()
                .jsonPath("$.abilities").value(hasSize(2));
    }

    @Test
    public void testListAbilitiesPokemonNotFound() {
        // Arrange
        String pokemonName = "unknown";
        when(listPokemonAbilitiesSortedByNameUseCase.execute(any()))
                .thenReturn(Mono.error(new PokemonNotFoundException(pokemonName)));

        // Act & Assert
        webTestClient.get()
                .uri("/v1/pokemon/{name}/abilities", pokemonName)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo(String.format("Pokémon with name '%s' not found.", pokemonName));
    }
}
