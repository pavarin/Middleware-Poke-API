package com.pavarin.middlewarepokeapi.infrastructure.api;

import com.pavarin.middlewarepokeapi.application.api.PokeAPI;
import com.pavarin.middlewarepokeapi.application.domain.pokemon.Ability;
import com.pavarin.middlewarepokeapi.application.exception.PokemonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
public class PokeAPIRest implements PokeAPI<String, Mono<List<Ability>>> {

    private final WebClient webClient;

    public PokeAPIRest(final WebClient webClient) {
        this.webClient = Objects.requireNonNull(webClient);
    }

    @Override
    public Mono<List<Ability>> listAbilities(String name) {

        return webClient.get()
                .uri("/pokemon/{name}", name.toLowerCase())
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> {
                            if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                                return Mono.error(new PokemonNotFoundException(name));
                            }
                            String reasonPhrase = HttpStatus.resolve(clientResponse.statusCode().value()) != null
                                    ? HttpStatus.resolve(clientResponse.statusCode().value()).getReasonPhrase()
                                    : "Unknown Status";
                            return Mono.error(new WebClientResponseException(
                                    clientResponse.statusCode().value(),
                                    reasonPhrase,
                                    null, null, null
                            ));
                        }
                )
                .bodyToMono(PokemonResponse.class)
                .flatMap(pokemonResponse -> Mono.just(pokemonResponse.abilities()));

    }

    private List<Ability> listAbilities(PokemonResponse pokemonResponse) {
        return pokemonResponse.abilities();
    }

    public record PokemonResponse(String name, List<Ability> abilities) {

        public PokemonResponse {
            Objects.requireNonNull(name);
            Objects.requireNonNull(abilities);
        }

    }

}
