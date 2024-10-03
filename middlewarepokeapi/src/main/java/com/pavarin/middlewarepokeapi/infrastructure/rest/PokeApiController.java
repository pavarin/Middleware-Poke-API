package com.pavarin.middlewarepokeapi.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavarin.middlewarepokeapi.application.usecase.GetPokemonAbilitiesUseCase;

import java.util.Objects;

@RestController
@RequestMapping("/v1/pokemon")
public class PokeApiController {

    private final GetPokemonAbilitiesUseCase getPokemonAbilitiesUseCase;

    public PokeApiController(final GetPokemonAbilitiesUseCase getPokemonAbilitiesUseCase) {
        this.getPokemonAbilitiesUseCase = Objects.requireNonNull(getPokemonAbilitiesUseCase);
    }

    @GetMapping("/{name}/abilities")
    public ResponseEntity<?> listAbilities(@PathVariable("name") String name) {

        try {

            return ResponseEntity.ok()
                    .body(getPokemonAbilitiesUseCase.execute(new GetPokemonAbilitiesUseCase.Input(name)));

        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body(e.getMessage());

        }

    }

}
