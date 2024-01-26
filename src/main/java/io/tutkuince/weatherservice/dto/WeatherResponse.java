package io.tutkuince.weatherservice.dto;

public record WeatherResponse(
        Request request,
        Location location,
        Current current
) {
}
