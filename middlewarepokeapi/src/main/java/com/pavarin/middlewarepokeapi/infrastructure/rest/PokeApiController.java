package com.pavarin.middlewarepokeapi.infrastructure.rest;

import com.pavarin.middlewarepokeapi.application.exception.PokemonNotFoundException;
import com.pavarin.middlewarepokeapi.application.usecase.ListPokemonAbilitiesSortedByNameUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/v1/pokemon")
public class PokeApiController {

    private final ListPokemonAbilitiesSortedByNameUseCase listPokemonAbilitiesSortedByNameUseCase;

    public PokeApiController(final ListPokemonAbilitiesSortedByNameUseCase listPokemonAbilitiesSortedByNameUseCase) {
        this.listPokemonAbilitiesSortedByNameUseCase = Objects.requireNonNull(listPokemonAbilitiesSortedByNameUseCase);
    }

    @GetMapping("/{name}/abilities")
    public Mono<ListPokemonAbilitiesSortedByNameUseCase.Output> listAbilities(@PathVariable("name") String name) {

        return listPokemonAbilitiesSortedByNameUseCase.execute(new ListPokemonAbilitiesSortedByNameUseCase.Input(name));

    }

    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<String> handlePokemonNotFound(PokemonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
