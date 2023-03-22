package com.pobopovola.gymanager_app.tasks;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class ListAsyncTask<T> extends BaseNetAsyncTask<List<T>> {

    public ListAsyncTask(RestTemplate restTemplate, Consumer<List<T>> processSuccess, Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    protected abstract ResponseEntity<T[]> makeArrayRequest();

    @Override
    protected ResponseEntity<List<T>> makeRequest() {
        return convertArrayToList(makeArrayRequest());
    }

    protected ResponseEntity<List<T>> convertArrayToList(ResponseEntity<T[]> responseEntity) {
        if (responseEntity.hasBody()) {
            return new ResponseEntity<>(
                    Arrays.asList(responseEntity.getBody()),
                    responseEntity.getStatusCode()
            );
        }
        return new ResponseEntity<>(responseEntity.getStatusCode());
    }
}
