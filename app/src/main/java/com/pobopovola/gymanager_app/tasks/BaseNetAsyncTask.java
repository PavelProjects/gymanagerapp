package com.pobopovola.gymanager_app.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pobopovola.gymanager_app.utils.NetUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public abstract class BaseNetAsyncTask<T> extends AsyncTask<Void, Void, ResponseEntity<T>> {
    private static final String LOGGER_TAG = BaseNetAsyncTask.class.getSimpleName();

    private final RestTemplate restTemplate;
    private Consumer<T> processSuccess;
    private Consumer<HttpStatus> processFailure;

    public BaseNetAsyncTask(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BaseNetAsyncTask(RestTemplate restTemplate, Consumer<T> processSuccess, Consumer<HttpStatus> processFailure) {
        this.restTemplate = restTemplate;
        this.processSuccess = processSuccess;
        this.processFailure = processFailure;
    }

    @Override
    protected void onPostExecute(ResponseEntity<T> result) {
        super.onPostExecute(result);
        if (NetUtils.isFailed(result)) {
            Log.e(LOGGER_TAG, "Request failed with code " + result.getStatusCode().value());
            if (processSuccess != null) {
                processFailure.accept(result.getStatusCode());
            }
            return;
        }
        if (processSuccess != null) {
            processSuccess.accept(result.getBody());
        }
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
