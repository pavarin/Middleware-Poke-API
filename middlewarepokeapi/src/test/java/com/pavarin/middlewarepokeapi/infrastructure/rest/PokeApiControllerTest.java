package com.pavarin.middlewarepokeapi.infrastructure.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;
import com.pavarin.middlewarepokeapi.application.exception.PokemonNotFoundException;
import com.pavarin.middlewarepokeapi.application.usecase.ListPokemonAbilitiesSortedByNameUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PokeApiControllerTest {

    @InjectMocks
    private PokeApiController pokeApiController;

    @Mock
    private ListPokemonAbilitiesSortedByNameUseCase listPokemonAbilitiesSortedByNameUseCase;

    @Test
    @DisplayName("Deve retornar as habilidades de um pokemor ordenadas com sucesso!")
    public void testListAbilitiesSuccess() {

        // Arrange
        String pokemonName = "pikachu";
        List<Ability> abilities = List.of(
                new Ability(new Ability.AbilityDetail("static", ""),
                        false,
                        1),
                new Ability(new Ability.AbilityDetail("lightning-rod", ""),
                        false
                        , 2));
        ListPokemonAbilitiesSortedByNameUseCase.Output output = new ListPokemonAbilitiesSortedByNameUseCase.Output(abilities);

        when(listPokemonAbilitiesSortedByNameUseCase.execute(any())).thenReturn(Mono.just(output));

        // Act
        Mono<ListPokemonAbilitiesSortedByNameUseCase.Output> responseMono = pokeApiController.listAbilities(pokemonName);
        ListPokemonAbilitiesSortedByNameUseCase.Output response = responseMono.block();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.abilities().size());
        verify(listPokemonAbilitiesSortedByNameUseCase, times(1)).execute(any());

    }

    @Test
    @DisplayName("Deve retornar erro 404 em caso de passagem de um nome de pokemon inexistente!")
    public void testListAbilitiesPokemonNotFound() {

        // Arrange
        String pokemonName = "unknown";
        when(listPokemonAbilitiesSortedByNameUseCase.execute(any()))
                .thenReturn(Mono.error(new PokemonNotFoundException("Pokémon não encontrado")));

        // Act
        Mono<ListPokemonAbilitiesSortedByNameUseCase.Output> responseMono = pokeApiController.listAbilities(pokemonName);

        // Assert
        assertThrows(PokemonNotFoundException.class, responseMono::block);
        verify(listPokemonAbilitiesSortedByNameUseCase, times(1)).execute(any());

    }

    @Test
    @DisplayName("Deve validar o tratamento de excessão para o caso de a poke api retornar 404!")
    public void testHandlePokemonNotFoundException() {

        // Arrange
        PokemonNotFoundException exception = new PokemonNotFoundException("teste");

        // Act
        ResponseEntity<String> response = pokeApiController.handlePokemonNotFound(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Pokémon with name 'teste' not found.", response.getBody());

    }

}
