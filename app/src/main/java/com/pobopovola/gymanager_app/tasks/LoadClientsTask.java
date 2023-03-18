package com.pobopovola.gymanager_app.tasks;

import com.pobopovola.gymanager_app.model.ClientInfo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LoadClientsTask extends BaseNetAsyncTask<List<ClientInfo>>{

    public LoadClientsTask(RestTemplate restTemplate, Consumer<List<ClientInfo>> processSuccess, Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    @Override
    protected ResponseEntity<List<ClientInfo>> doInBackground(Void... voids) {
        List<ClientInfo> result = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ClientInfo clientInfo = new ClientInfo();
            String iStr = String.valueOf(i);
            clientInfo.setId(iStr);
            clientInfo.setFirstName("Client number " + iStr);
            result.add(clientInfo);
        }
        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }
}
