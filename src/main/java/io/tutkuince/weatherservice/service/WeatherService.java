package io.tutkuince.weatherservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tutkuince.weatherservice.constants.Constants;
import io.tutkuince.weatherservice.dto.WeatherDto;
import io.tutkuince.weatherservice.dto.WeatherResponse;
import io.tutkuince.weatherservice.model.Weather;
import io.tutkuince.weatherservice.repository.WeatherRepository;
import io.tutkuince.weatherservice.util.DateUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherDto getWeatherByCityName(String cityName) {
        Optional<Weather> weatherOptional = weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(cityName);

        return weatherOptional.map(weather -> {
            if (weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30))) {
                return WeatherDto.convertToWeatherDto(getWeatherFromWeatherStack(cityName));
            }
            return WeatherDto.convertToWeatherDto(weather);
        }).orElseGet(() -> WeatherDto.convertToWeatherDto(getWeatherFromWeatherStack(cityName)));
    }

    private Weather getWeatherFromWeatherStack(String cityName) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getWeatherStackUrl(cityName), String.class);
        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
            return saveWeather(cityName, weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Weather saveWeather(String cityName, WeatherResponse weatherResponse) {
        Weather weather = new Weather(
                cityName,
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localTime(), DateUtil.formatDateTime())
        );
        return weatherRepository.save(weather);
    }

    private String getWeatherStackUrl(String cityName) {
        return Constants.API_URL + Constants.ACCESS_KEY_PARAM + Constants.API_KEY + Constants.QUERY_KEY_PARAM + cityName;
    }
}
