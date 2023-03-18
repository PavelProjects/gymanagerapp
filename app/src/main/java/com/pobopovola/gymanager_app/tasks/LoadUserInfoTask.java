package com.pobopovola.gymanager_app.tasks;

import android.util.Log;

import com.pobopovola.gymanager_app.API;
import com.pobopovola.gymanager_app.CreditsHolder;
import com.pobopovola.gymanager_app.model.UserInfo;
import com.pobopovola.gymanager_app.utils.NetUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public class LoadUserInfoTask extends BaseNetAsyncTask<UserInfo> {
    private static final String LOGGER_TAG = LoadUserInfoTask.class.getSimpleName();

    private String login;

    public LoadUserInfoTask(RestTemplate restTemplate, Consumer<UserInfo> processSuccess, Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    public LoadUserInfoTask setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    protected ResponseEntity<UserInfo> doInBackground(Void... voids) {
        if (StringUtils.isBlank(login)) {
            if (CreditsHolder.getCredits() == null) {
                Log.e(LOGGER_TAG, "login parameter is missing!");
                return null;
            }
            login = CreditsHolder.getCredits().getLogin();
        }

        return getRestTemplate().exchange(
                API.USER_INFO_URL,
                HttpMethod.GET,
                new HttpEntity<>(NetUtils.buildAuthHeader()),
                UserInfo.class,
                login
        );
    }
}
