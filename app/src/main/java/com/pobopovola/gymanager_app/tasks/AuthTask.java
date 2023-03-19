package com.pobopovola.gymanager_app.tasks;

import android.util.Log;

import com.pobopovola.gymanager_app.API;
import com.pobopovola.gymanager_app.CreditsHolder;
import com.pobopovola.gymanager_app.model.AuthCredits;
import com.pobopovola.gymanager_app.model.UserToken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public class AuthTask extends BaseNetAsyncTask<UserToken> {
    private static final String LOGGER_TAG = AuthTask.class.getSimpleName();

    public AuthTask(
            RestTemplate restTemplate,
            Consumer<UserToken> processSuccess,
            Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    @Override
    protected ResponseEntity<UserToken> makeRequest() {
        AuthCredits authCredits = CreditsHolder.getCredits();
        if (authCredits == null) {
            Log.e(LOGGER_TAG, "Credits are empty!");
            return null;
        }

        return getRestTemplate().postForEntity(
                API.LOGIN_URL,
                authCredits,
                UserToken.class
        );
    }
}
