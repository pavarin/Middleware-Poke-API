package com.pavarin.middlewarepokeapi.application;

import reactor.core.publisher.Mono;

public interface UseCaseReactive<INPUT, OUTPUT> {

    Mono<OUTPUT> execute(INPUT input);

}
