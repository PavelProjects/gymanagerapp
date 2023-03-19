package com.pobopovola.gymanager_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.adapter.ExerciseAdapter;
import com.pobopovola.gymanager_app.model.WorkoutInfo;
import com.pobopovola.gymanager_app.tasks.LoadWorkoutInfoTask;
import com.pobopovola.gymanager_app.utils.DateUtils;
import com.pobopovola.gymanager_app.utils.RestTemplateBuilder;
import com.pobopovola.gymanager_app.utils.ViewUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class WorkoutViewActivity extends AppCompatActivity {
    private static final String LOGGER_TAG = WorkoutViewActivity.class.getSimpleName();
    public static final String WORKOUT_ID_EXTRA = "workoutId";

    private final RestTemplate restTemplate = RestTemplateBuilder.buildDefault();
    private ExerciseAdapter exerciseAdapter;

    private Context context;
    private String workoutId;
    private WorkoutInfo workoutInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_view);
        context = getBaseContext();

        workoutId = getIntent().getStringExtra(WORKOUT_ID_EXTRA);

        if (StringUtils.isBlank(workoutId)) {
            Log.e(LOGGER_TAG, "Missing workout id");
            Toast.makeText(context, "Wops, smth gone wrong :(", Toast.LENGTH_SHORT).show();
        }

        ListView exercisesListView = findViewById(R.id.workout_exercises);
        exerciseAdapter = new ExerciseAdapter(this, R.layout.exercise_item_layout, new ArrayList<>());
        exercisesListView.setAdapter(exerciseAdapter);

        loadData();
    }

    private void loadData() {
        new LoadWorkoutInfoTask(
                restTemplate,
                workout -> {
                    workoutInfo = workout;
                    updateFields();
                },
                code -> {
                    Toast.makeText(context, "Can't load workout :(", Toast.LENGTH_SHORT).show();
                }
        ).setWorkoutId(workoutId).execute();
    }

    private void updateFields() {
        if (workoutInfo != null) {
            ViewUtils.setTextIfViewExists(findViewById(R.id.workout_start_date), DateUtils.dateToStringClient(workoutInfo.getStartDate()));
            ViewUtils.setTextIfViewExists(findViewById(R.id.workout_description), workoutInfo.getDescription());

            exerciseAdapter.clear();
            exerciseAdapter.addAll(workoutInfo.getExercises());
        }
    }
}
