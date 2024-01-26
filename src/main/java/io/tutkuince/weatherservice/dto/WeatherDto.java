package io.tutkuince.weatherservice.dto;

import io.tutkuince.weatherservice.model.Weather;

public record WeatherDto(
        String cityName,
        String country,
        Integer temperature
) {
    public static WeatherDto convertToWeatherDto(Weather from) {
        return new WeatherDto(from.getCityName(), from.getCountry(), from.getTemperature());
    }
}
