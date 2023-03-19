package com.pobopovola.gymanager_app.tasks;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import android.os.AsyncTask;
import android.util.Log;

import com.pobopovola.gymanager_app.exception.ResponseException;
import com.pobopovola.gymanager_app.utils.NetUtils;
import com.pobopovola.gymanager_app.utils.RestTemplateBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
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

        this.restTemplate.setErrorHandler(new BaseResponseErrorHandler());
    }

    protected abstract ResponseEntity<T> makeRequest();

    @Override
    protected ResponseEntity<T> doInBackground(Void... voids) {
        try {
            return makeRequest();
        } catch (ResponseException exception) {
            return new ResponseEntity<>(exception.getStatus());
        } catch (RuntimeException exception) {
            Log.e(LOGGER_TAG, "Failed to process request ", exception);
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<T> result) {
        super.onPostExecute(result);
        if (NetUtils.isFailed(result)) {
            if (processSuccess != null) {
                processFailure.accept(result != null ? result.getStatusCode() : HttpStatus.I_AM_A_TEAPOT);
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

    static class BaseResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().series() == CLIENT_ERROR
                            || response.getStatusCode().series() == SERVER_ERROR;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            throw new ResponseException(response.getStatusCode());
        }
    }
}
