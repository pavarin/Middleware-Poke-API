package com.pavarin.middlewarepokeapi.application;

public interface UseCase<INPUT, OUTPUT> {

    OUTPUT execute(INPUT input);

}
