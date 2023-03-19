package com.pobopovola.gymanager_app.tasks;

import android.util.Log;

import com.pobopovola.gymanager_app.API;
import com.pobopovola.gymanager_app.model.WorkoutInfo;
import com.pobopovola.gymanager_app.utils.NetUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public class LoadWorkoutInfoTask extends BaseNetAsyncTask<WorkoutInfo> {
    private String workoutId;

    public LoadWorkoutInfoTask(RestTemplate restTemplate, Consumer<WorkoutInfo> processSuccess, Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
    }

    public LoadWorkoutInfoTask setWorkoutId(String id) {
        workoutId = id;
        return this;
    }

    @Override
    protected ResponseEntity<WorkoutInfo> makeRequest() {
        if (StringUtils.isBlank(workoutId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return getRestTemplate().exchange(
                API.WORKOUT_BY_ID,
                HttpMethod.GET,
                new HttpEntity<>(NetUtils.buildAuthHeader()),
                WorkoutInfo.class,
                workoutId
        );
    }
}
