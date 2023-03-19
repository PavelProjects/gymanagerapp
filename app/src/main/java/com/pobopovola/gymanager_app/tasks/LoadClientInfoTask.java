package com.pobopovola.gymanager_app.tasks;

import com.pobopovola.gymanager_app.API;
import com.pobopovola.gymanager_app.model.ClientInfo;
import com.pobopovola.gymanager_app.utils.NetUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public class LoadClientInfoTask extends BaseNetAsyncTask<ClientInfo> {
    private final String clientId;

    public LoadClientInfoTask(
            RestTemplate restTemplate,
            String clientId,
            Consumer<ClientInfo> processSuccess,
            Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
        this.clientId = clientId;
    }

    @Override
    protected ResponseEntity<ClientInfo> makeRequest() {
        return getRestTemplate().exchange(
                API.CLIENT_BY_ID_URL,
                HttpMethod.GET,
                new HttpEntity<>(NetUtils.buildAuthHeader()),
                ClientInfo.class,
                clientId
        );
    }
}
