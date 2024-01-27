package io.tutkuince.weatherservice.util;

import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static DateTimeFormatter formatDateTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
}
