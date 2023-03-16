package com.pobopovola.gymanager_app;


import com.pobopovola.gymanager_app.model.UserToken;

public class CreditsHolder {
    private static UserToken token;

    public static UserToken getToken() {
        return token;
    }


    public static synchronized void setToken(UserToken token) {
        CreditsHolder.token = token;
    }
}
