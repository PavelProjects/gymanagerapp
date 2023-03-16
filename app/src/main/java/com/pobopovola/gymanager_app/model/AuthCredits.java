package com.pobopovola.gymanager_app.model;

import com.google.gson.annotations.SerializedName;

public class AuthCredits {
    private String login;
    private String password;

    public AuthCredits() {
    }

    public AuthCredits(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
