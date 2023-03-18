package com.pobopovola.gymanager_app.tasks;

import com.pobopovola.gymanager_app.model.Client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LoadClientsTask extends BaseNetAsyncTask<List<Client>>{

    public LoadClientsTask(RestTemplate restTemplate, Consumer<List<Client>> processSuccess, Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    @Override
    protected ResponseEntity<List<Client>> doInBackground(Void... voids) {
        List<Client> result = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Client client = new Client();
            String iStr = String.valueOf(i);
            client.setId(iStr);
            client.setLogin("client_" + iStr);
            client.setFirstName("Client number " + iStr);
            result.add(client);
        }
        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }
}
