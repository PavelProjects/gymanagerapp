package com.pobopovola.gymanager_app.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

public class RestTemplateBuilder {
    public static RestTemplate buildDefault() {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
                    try {
                        return DateUtils.dateFromString(json.getAsString());
                    } catch (Exception ex) {
                        Log.e("GSON_PARSER", "Failed to parse date: " + ex.getMessage());
                        return LocalDateTime.now();
                    }
                }).create();

        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter(gson));
        return restTemplate;
    }
}
