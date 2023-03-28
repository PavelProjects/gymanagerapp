package com.pobopovola.gymanager_app.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class RestTemplateBuilder {
    public static RestTemplate buildDefault() {
        RestTemplate restTemplate = new RestTemplate();
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
                    try {
                        return DateUtils.dateTimeFromString(json.getAsString());
                    } catch (Exception ex) {
                        Log.e("GSON_PARSER", "Failed to parse date: " + ex.getMessage());
                        return LocalDateTime.now();
                    }
                });

        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter(gson.create()));
        return restTemplate;
    }
}
