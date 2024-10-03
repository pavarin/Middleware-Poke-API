package com.pavarin.middlewarepokeapi.application.domain.pokemon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class Pokemon {

    private String name;
    private List<Ability> abilities;

}
