package com.pobopovola.gymanager_app.tasks;

import android.util.Log;

import com.pobopovola.gymanager_app.API;
import com.pobopovola.gymanager_app.model.ClientInfo;
import com.pobopovola.gymanager_app.utils.NetUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public class CreateUpdateClientTask extends BaseNetAsyncTask<ClientInfo> {
    private ClientInfo clientInfo;

    public CreateUpdateClientTask(RestTemplate restTemplate, Consumer<ClientInfo> processSuccess, Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    public CreateUpdateClientTask setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
        return this;
    }

    @Override
    protected ResponseEntity<ClientInfo> makeRequest() {
        if (clientInfo == null) {
            Log.e(CreateUpdateClientTask.class.getSimpleName(), "Client info is missing!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = NetUtils.buildAuthHeader();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientInfo> entity = new HttpEntity<>(clientInfo, headers);

        if (StringUtils.isBlank(clientInfo.getId())) {
            return getRestTemplate().exchange(
                    API.CLIENT_CREATE_URL,
                    HttpMethod.POST,
                    entity,
                    ClientInfo.class
            );

        } else {
            return getRestTemplate().exchange(
                    API.CLIENT_UPDATE_URL,
                    HttpMethod.PUT,
                    entity,
                    ClientInfo.class
            );

        }
    }
}
