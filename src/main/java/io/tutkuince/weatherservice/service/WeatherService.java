package io.tutkuince.weatherservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tutkuince.weatherservice.constants.Constants;
import io.tutkuince.weatherservice.dto.WeatherDto;
import io.tutkuince.weatherservice.dto.WeatherResponse;
import io.tutkuince.weatherservice.model.Weather;
import io.tutkuince.weatherservice.repository.WeatherRepository;
import io.tutkuince.weatherservice.util.DateUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = { "weathers" })
public class WeatherService {

    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    @Cacheable(key = "#cityName")
    public WeatherDto getWeatherByCityName(String cityName) {
        Optional<Weather> weatherOptional = weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(cityName);

        return weatherOptional.map(weather -> {
            if (weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30))) {
                return WeatherDto.convertToWeatherDto(getWeatherFromWeatherStack(cityName));
            }
            return WeatherDto.convertToWeatherDto(weather);
        }).orElseGet(() -> WeatherDto.convertToWeatherDto(getWeatherFromWeatherStack(cityName)));
    }

    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRateString = "10000")
    public void clearCache() {
        logger.info("Cache cleared");
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
