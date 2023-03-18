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

public class AuthUserTask extends BaseNetAsyncTask<UserToken> {
    private static final String LOGGER_TAG = AuthUserTask.class.getSimpleName();

    public AuthUserTask(
            RestTemplate restTemplate,
            Consumer<UserToken> processSuccess,
            Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }


    @Override
    protected ResponseEntity<UserToken> doInBackground(Void... voids) {
        try {
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
        } catch (Exception ex) {
            Log.i(LOGGER_TAG, "Failed to auth");
            Log.e(LOGGER_TAG, ex.getMessage());
            return null;
        }
    }
}
