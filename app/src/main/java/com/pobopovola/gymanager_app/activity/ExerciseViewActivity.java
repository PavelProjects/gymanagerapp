package com.pobopovola.gymanager_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;

import com.google.gson.Gson;
import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.adapter.ExerciseResultAdapter;
import com.pobopovola.gymanager_app.adapter.ExerciseTypeAdapter;
import com.pobopovola.gymanager_app.model.ExerciseInfo;
import com.pobopovola.gymanager_app.model.ExerciseResultInfo;
import com.pobopovola.gymanager_app.model.ExerciseTypeInfo;
import com.pobopovola.gymanager_app.tasks.LoadExerciseTypesTask;
import com.pobopovola.gymanager_app.utils.RestTemplateBuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ExerciseViewActivity extends BaseEditableActivity {
    public static final String EXERCISE_ID_EXTRA = "exercise_id";

    private final Context context = getBaseContext();
    private final RestTemplate restTemplate = RestTemplateBuilder.buildDefault();
    private ExerciseInfo exerciseInfo;

    private ExerciseTypeAdapter exerciseTypeAdapter;
    private ExerciseResultAdapter exerciseResultAdapter;

    public ExerciseViewActivity() {
        super(R.layout.exercise_view);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        String exerciseJson = getIntent().getStringExtra(EXERCISE_ID_EXTRA);
        if (StringUtils.isBlank(exerciseJson)) {
            finish();
        }
        Gson gson = new Gson();
        exerciseInfo = gson.fromJson(exerciseJson, ExerciseInfo.class);

        loadData();
        updateView();
    }

    @Override
    protected void initFields() {
        Spinner exerciseTypeSpinner = findViewById(R.id.exercise_type);
        exerciseTypeAdapter = new ExerciseTypeAdapter(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        exerciseTypeSpinner.setAdapter(exerciseTypeAdapter);

        exerciseResultAdapter = new ExerciseResultAdapter(this, R.layout.exercise_result_item_layout, new ArrayList<>());
        ListView exerciseResultsView = findViewById(R.id.exercise_results);
        exerciseResultsView.setAdapter(exerciseResultAdapter);

        findViewById(R.id.add_exercise_result).setOnClickListener(view -> {
            exerciseResultAdapter.add(new ExerciseResultInfo());
        });

        exerciseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ExerciseTypeInfo exerciseTypeInfo = (ExerciseTypeInfo) parent.getItemAtPosition(position);
                if (exerciseTypeInfo != null) {
                    getSupportActionBar().setTitle(exerciseTypeInfo.getDescription());
                    exerciseInfo.setExerciseType(exerciseTypeInfo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void saveObject() {
        //todo
        setEditable(false);
    }

    private void loadData() {
        new LoadExerciseTypesTask(
                restTemplate,
                result -> {
                    exerciseTypeAdapter.clear();
                    exerciseTypeAdapter.addAll(result);
                },
                null
        ).execute();
    }

    protected void updateFields() {
        if (exerciseInfo != null) {
            if (exerciseInfo.getResults() != null) {
                exerciseResultAdapter.clear();
                exerciseResultAdapter.addAll(exerciseInfo.getResults());
            }
        }
    }
}
