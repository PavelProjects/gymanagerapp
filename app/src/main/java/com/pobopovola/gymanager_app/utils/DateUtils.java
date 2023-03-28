package com.pobopovola.gymanager_app.utils;

import android.annotation.SuppressLint;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    public static final String SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String CLIENT_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    public static final String CLIENT_DATE_FORMAT = "dd.MM.yyyy";

    public static String dateTimeToStringClient(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(CLIENT_DATE_TIME_FORMAT));
    }

    public static LocalDateTime dateTimeFromString(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        int dotIndex = date.indexOf('.');
        String dateFixed = dotIndex > 0 ? date.substring(0,  dotIndex) : date;
        return LocalDateTime.parse(dateFixed, DateTimeFormatter.ofPattern(SERVER_DATE_TIME_FORMAT));
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateToStringClient(Date date) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(CLIENT_DATE_FORMAT).format(date);
    }
}
