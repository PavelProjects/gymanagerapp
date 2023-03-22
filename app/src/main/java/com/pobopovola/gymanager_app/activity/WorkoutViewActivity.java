package com.pobopovola.gymanager_app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.adapter.ExerciseAdapter;
import com.pobopovola.gymanager_app.model.ExerciseInfo;
import com.pobopovola.gymanager_app.model.WorkoutInfo;
import com.pobopovola.gymanager_app.tasks.LoadWorkoutInfoTask;
import com.pobopovola.gymanager_app.utils.DateUtils;
import com.pobopovola.gymanager_app.utils.RestTemplateBuilder;
import com.pobopovola.gymanager_app.utils.ViewUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class WorkoutViewActivity extends BaseEditableActivity {
    private static final String LOGGER_TAG = WorkoutViewActivity.class.getSimpleName();
    public static final String WORKOUT_ID_EXTRA = "workoutId";

    private final RestTemplate restTemplate = RestTemplateBuilder.buildDefault();
    private ExerciseAdapter exerciseAdapter;

    private Context context;
    private String workoutId;
    private WorkoutInfo workoutInfo;

    private EditText startDateEdit;
    private EditText descriptionEdit;

    public WorkoutViewActivity() {
        super(R.layout.workout_view);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setTitle("Запись тренировки");

        context = getBaseContext();
//        workoutId = getIntent().getStringExtra(WORKOUT_ID_EXTRA);
        workoutId = "1"; // TODO
        setEditable(StringUtils.isBlank(workoutId));

        loadData();
        updateFields();
    }

    @Override
    protected void initFields() {
        startDateEdit = findViewById(R.id.workout_start_date);
        descriptionEdit = findViewById(R.id.workout_description);

        ListView exercisesListView = findViewById(R.id.workout_exercises);
        exerciseAdapter = new ExerciseAdapter(this, R.layout.exercise_item_layout, new ArrayList<>());
        exercisesListView.setAdapter(exerciseAdapter);
        exercisesListView.setOnItemClickListener((parent, view, position, id) -> {
            ExerciseInfo exerciseInfo = (ExerciseInfo) parent.getItemAtPosition(position);
            if (exerciseInfo != null) {
                Gson gson = new Gson();
                Intent intent = new Intent(WorkoutViewActivity.this, ExerciseViewActivity.class);
                intent.putExtra(ExerciseViewActivity.EXERCISE_ID_EXTRA, gson.toJson(exerciseInfo));
                startActivity(intent);
            }
        });

        findViewById(R.id.add_exercise).setOnClickListener(view -> {
            Gson gson = new Gson();
            Intent intent = new Intent(WorkoutViewActivity.this, ExerciseViewActivity.class);
            intent.putExtra(ExerciseViewActivity.EXERCISE_ID_EXTRA, gson.toJson(new ExerciseInfo()));
            startActivity(intent);
        });
    }

    @Override
    protected void saveObject() {
        //todo
        setEditable(false);
    }

    private void loadData() {
        if (StringUtils.isBlank(workoutId)) {
            return;
        }

        new LoadWorkoutInfoTask(
                restTemplate,
                workout -> {
                    workoutInfo = workout;
                    updateView();
                },
                code -> {
                    Toast.makeText(context, "Can't load workout :(", Toast.LENGTH_SHORT).show();
                }
        ).setWorkoutId(workoutId).execute();
    }

    protected void updateFields() {
        startDateEdit.setEnabled(isEditable());
        descriptionEdit.setEnabled(isEditable());


        if (workoutInfo != null) {
            startDateEdit.setText(DateUtils.dateToStringClient(workoutInfo.getStartDate()));
            descriptionEdit.setText(workoutInfo.getDescription());

            exerciseAdapter.clear();
            if (workoutInfo.getStartDate() != null) {
                exerciseAdapter.addAll(workoutInfo.getExercises());
            }
        }
    }
}
