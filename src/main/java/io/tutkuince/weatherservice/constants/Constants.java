package io.tutkuince.weatherservice.constants;

import org.springframework.beans.factory.annotation.Value;

public class Constants {

    public static String API_URL;
    public static String API_KEY;
    public static String ACCESS_KEY_PARAM = "?access_key";
    public static String QUERY_KEY_PARAM = "&query=";

    @Value("${weather-stack.api-url}")
    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    @Value("${weather-stack.api-key}")
    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
}