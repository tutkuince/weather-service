package io.tutkuince.weatherservice.dto;

import io.tutkuince.weatherservice.model.Weather;
import io.tutkuince.weatherservice.util.DateUtil;

import java.time.LocalDateTime;

public record WeatherDto(
        String cityName,
        String country,
        Integer temperature,
        String updatedTime
) {
    public static WeatherDto convertToWeatherDto(Weather from) {
        return new WeatherDto(
                from.getCityName(),
                from.getCountry(),
                from.getTemperature(),
                from.getUpdatedTime().format(DateUtil.formatDateTime())
        );
    }
}
