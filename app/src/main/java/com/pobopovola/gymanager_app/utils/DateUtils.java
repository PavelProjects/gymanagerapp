package com.pobopovola.gymanager_app.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final String SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String CLIENT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static String dateToStringClient(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(CLIENT_DATE_TIME_FORMAT));
    }

    public static LocalDateTime dateFromString(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        int dotIndex = date.indexOf('.');
        String dateFixed = dotIndex > 0 ? date.substring(0,  dotIndex) : date;
        return LocalDateTime.parse(dateFixed, DateTimeFormatter.ofPattern(SERVER_DATE_TIME_FORMAT));
    }
}
