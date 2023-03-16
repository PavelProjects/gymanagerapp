package com.pobopovola.gymanager_app.model;

import androidx.annotation.NonNull;

public class UserToken {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public String toString() {
        return token;
    }
}
