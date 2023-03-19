package com.pobopovola.gymanager_app.tasks;

import android.util.Log;

import com.pobopovola.gymanager_app.API;
import com.pobopovola.gymanager_app.model.ClientInfo;
import com.pobopovola.gymanager_app.utils.NetUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class LoadClientsTask extends ListAsyncTask<ClientInfo>{

    public LoadClientsTask(RestTemplate restTemplate, Consumer<List<ClientInfo>> processSuccess, Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    @Override
    protected ResponseEntity<List<ClientInfo>> makeRequest() {
        ResponseEntity<ClientInfo[]> clientInfos = getRestTemplate().exchange(
                API.CLIENT_ALL_URL,
                HttpMethod.GET,
                new HttpEntity<>(NetUtils.buildAuthHeader()),
                ClientInfo[].class
        );
        return convertArrayToList(clientInfos);
    }
}
