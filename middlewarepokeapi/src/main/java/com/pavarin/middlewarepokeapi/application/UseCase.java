package com.pavarin.middlewarepokeapi.application;

import reactor.core.publisher.Mono;

public interface UseCase<INPUT, OUTPUT> {

    OUTPUT execute(INPUT input);

}
