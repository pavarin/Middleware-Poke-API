package com.pavarin.middlewarepokeapi.application.domain.pokemon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Ability {

    @JsonProperty("ability")
    private AbilityDetail abilityDetail;
    private boolean is_hidden;
    private int slot;

    @Data
    public static class AbilityDetail {

        private String name;
        private String url;

    }

}
