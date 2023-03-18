package com.pobopovola.gymanager_app.tasks;

import com.pobopovola.gymanager_app.model.ClientInfo;
import com.pobopovola.gymanager_app.model.ExerciseInfo;
import com.pobopovola.gymanager_app.model.ExerciseResultInfo;
import com.pobopovola.gymanager_app.model.WorkoutInfo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.function.Consumer;

public class LoadClientInfoTask extends BaseNetAsyncTask<ClientInfo> {
    private final String clientId;
    private boolean loadFull = false;

    public LoadClientInfoTask(
            RestTemplate restTemplate,
            String clientId,
            Consumer<ClientInfo> processSuccess,
            Consumer<HttpStatus> processFailure) {
        super(restTemplate, processSuccess, processFailure);
        this.clientId = clientId;
    }

    public LoadClientInfoTask loadFull(boolean loadFull) {
        this.loadFull = loadFull;
        return this;
    }

    @Override
    protected ResponseEntity<ClientInfo> doInBackground(Void... voids) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setId(clientId);
        clientInfo.setFirstName("Client number " + clientId);
        clientInfo.setPhone("8192381923");
        clientInfo.setDescription("User description");
        clientInfo.setBirthDate(new Date());

        if (loadFull) {
            for (int n = 0; n < 3; n++) {
                String nStr = String.valueOf(n);
                WorkoutInfo workout = new WorkoutInfo();
                workout.setId("workout_id_" + nStr);
                workout.setStartDate(new Date());
                workout.setDescription("workout description " + nStr);

                for(int i = 0; i < 5; i++) {
                    String iStr = String.valueOf(i);
                    ExerciseInfo exercise = new ExerciseInfo();
                    exercise.setId(iStr);
                    exercise.setNote("note " + iStr);
                    exercise.setType("type " + iStr);
                    for(int j = 0; j < 3; j++) {
                        ExerciseResultInfo resultInfo = new ExerciseResultInfo();
                        resultInfo.setId(String.valueOf(j));
                        resultInfo.setResult(j + i);
                        exercise.addResult(resultInfo);
                    }
                    workout.addExercise(exercise);
                }

                clientInfo.addWorkoutInfo(workout);
            }
        }

        return new ResponseEntity<>(clientInfo, HttpStatus.OK);
    }
}
