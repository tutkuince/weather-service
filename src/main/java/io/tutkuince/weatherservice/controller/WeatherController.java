package io.tutkuince.weatherservice.controller;

import io.tutkuince.weatherservice.dto.WeatherDto;
import io.tutkuince.weatherservice.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<WeatherDto> getWeatherByCityName(@PathVariable String cityName) {
        return ResponseEntity.ok(weatherService.getWeatherByCityName(cityName));
    }
}
