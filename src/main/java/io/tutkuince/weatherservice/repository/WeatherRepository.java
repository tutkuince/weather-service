package io.tutkuince.weatherservice.repository;

import io.tutkuince.weatherservice.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, String> {
    public Optional<Weather> findFirstByRequestedCityNameOrderByUpdatedTimeDesc(String cityName);
}
