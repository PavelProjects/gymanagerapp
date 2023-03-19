package com.pobopovola.gymanager_app;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.pobopovola.gymanager_app.model.AuthCredits;
import com.pobopovola.gymanager_app.model.UserInfo;
import com.pobopovola.gymanager_app.model.UserToken;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class CreditsHolder {
    private static UserToken token;
    private static AuthCredits credits;

    public static void loadFromPreferences(Context context) {
        if (token != null && credits != null) {
            return;
        }

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.credits_preferences), Context.MODE_PRIVATE);

        String tokenValue = sharedPreferences.getString(context.getString(R.string.token_preferences), null);
        String login = sharedPreferences.getString(context.getString(R.string.login_preferences), null);
        String password = sharedPreferences.getString(context.getString(R.string.password_preferences), null);

        if (StringUtils.isNotBlank(tokenValue)) {
            token = new UserToken(tokenValue);
        }

        if (StringUtils.isNotBlank(login) && StringUtils.isNotBlank(password)) {
            credits = new AuthCredits(login, password);
        }
    }

    public static void saveToPreferences(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.credits_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(context.getString(R.string.token_preferences), token != null ? token.getToken() : "");
        editor.putString(context.getString(R.string.login_preferences), credits != null ? credits.getLogin() : "");
        editor.putString(context.getString(R.string.password_preferences), credits != null ? credits.getPassword() : "");

        editor.apply();
    }

    @Nullable
    public static UserToken getToken() {
        return token;
    }

    public static synchronized void setToken(UserToken token) {
        CreditsHolder.token = token;
    }

    @Nullable
    public static AuthCredits getCredits() {
        return credits;
    }

    public static synchronized void setCredits(AuthCredits newCredits) {
        credits = newCredits;
    }
}
