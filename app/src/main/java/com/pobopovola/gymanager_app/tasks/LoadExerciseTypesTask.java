package com.pobopovola.gymanager_app.tasks;

import com.pobopovola.gymanager_app.API;
import com.pobopovola.gymanager_app.model.ExerciseTypeInfo;
import com.pobopovola.gymanager_app.utils.NetUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Consumer;

public class LoadExerciseTypesTask extends ListAsyncTask<ExerciseTypeInfo> {
    public LoadExerciseTypesTask(RestTemplate restTemplate, Consumer<List<ExerciseTypeInfo>> processSuccess, Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    @Override
    protected ResponseEntity<ExerciseTypeInfo[]> makeArrayRequest() {
        return getRestTemplate().exchange(
                API.EXERCISE_TYPES_URL,
                HttpMethod.GET,
                new HttpEntity<>(NetUtils.buildAuthHeader()),
                ExerciseTypeInfo[].class
        );
    }
}
