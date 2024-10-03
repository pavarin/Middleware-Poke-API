package com.pavarin.middlewarepokeapi.application.domain.pokemon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ability {

    @JsonProperty("ability")
    private AbilityDetail abilityDetail;
    private boolean is_hidden;
    private int slot;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AbilityDetail {

        private String name;
        private String url;

    }

}
