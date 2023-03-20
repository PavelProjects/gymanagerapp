package com.pobopovola.gymanager_app.utils;

import static org.springframework.http.HttpStatus.Series.SUCCESSFUL;

import com.pobopovola.gymanager_app.CreditsHolder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class NetUtils {
    public static boolean isFailed(ResponseEntity responseEntity) {
        return responseEntity == null || responseEntity.getStatusCode().series() != SUCCESSFUL || !responseEntity.hasBody();
    }

    public static HttpHeaders buildAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + CreditsHolder.getToken().getToken());
        return headers;
    }
}
