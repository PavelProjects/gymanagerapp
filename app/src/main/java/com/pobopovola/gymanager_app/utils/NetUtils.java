package com.pobopovola.gymanager_app.utils;

import com.pobopovola.gymanager_app.CreditsHolder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class NetUtils {
    public static boolean isFailed(ResponseEntity responseEntity) {
        return responseEntity == null || responseEntity.getStatusCode().value() != 200 || responseEntity.getBody() == null;
    }

    public static HttpHeaders buildAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + CreditsHolder.getToken().getToken());
        return headers;
    }
}