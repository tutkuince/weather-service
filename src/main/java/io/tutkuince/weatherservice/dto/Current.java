package io.tutkuince.weatherservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Current(
        Integer temperature
) {
}
