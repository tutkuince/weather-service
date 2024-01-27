package io.tutkuince.weatherservice.controller;

import io.tutkuince.weatherservice.controller.validation.CityNameConstraint;
import io.tutkuince.weatherservice.dto.WeatherDto;
import io.tutkuince.weatherservice.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/weather")
@Validated
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<WeatherDto> getWeatherByCityName(@PathVariable @NotBlank @CityNameConstraint String cityName) {
        return ResponseEntity.ok(weatherService.getWeatherByCityName(cityName));
    }
}
