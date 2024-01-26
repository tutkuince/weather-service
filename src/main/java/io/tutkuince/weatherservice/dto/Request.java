package io.tutkuince.weatherservice.dto;

public record Request(
        String type,
        String query,
        String language,
        String unit
) {
}
