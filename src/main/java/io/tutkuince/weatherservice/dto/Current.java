package io.tutkuince.weatherservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Current(
        @JsonProperty("observation_time")
        String observationTime,
        Integer temperature,
        @JsonProperty("weather_code")
        Integer weather_code,
        @JsonProperty("weather_icons")
        List<String> weatherIcons,
        @JsonProperty("weather_descriptions")
        List<String> weatherDescriptions,
        @JsonProperty("wind_speed")
        Integer windSpeed,
        @JsonProperty("wind_degree")
        Integer windDegree,
        @JsonProperty("wind_dir")
        String windDir,
        Integer pressure,
        @JsonProperty("precip")
        Integer preCip,
        Integer humidity,
        @JsonProperty("cloudcover")
        Integer cloudCover,
        @JsonProperty("feelslike")
        Integer feelsLike,
        @JsonProperty("uv_index")
        Integer uvIndex,
        Integer visibility,
        @JsonProperty("is_day")
        String isDay
) {
}
