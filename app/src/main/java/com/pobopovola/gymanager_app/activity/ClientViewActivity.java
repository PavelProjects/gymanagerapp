package com.pobopovola.gymanager_app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.adapter.WorkoutAdapter;
import com.pobopovola.gymanager_app.model.ClientInfo;
import com.pobopovola.gymanager_app.model.WorkoutInfo;
import com.pobopovola.gymanager_app.tasks.LoadClientInfoTask;
import com.pobopovola.gymanager_app.utils.ViewUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ClientViewActivity extends AppCompatActivity {
    public static final String CLIENT_ID_EXTRA = "clientId";

    private final RestTemplate restTemplate = new RestTemplate();

    private Context context;
    private String clientId;
    private ClientInfo clientInfo;

    private WorkoutAdapter workoutAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_view);
        context = getBaseContext();

        Intent intent = getIntent();
        clientId = intent.getStringExtra(CLIENT_ID_EXTRA);
        if (StringUtils.isEmpty(clientId)) {
            finish();
        }

        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        ListView workoutListView = findViewById(R.id.client_workouts);
        workoutAdapter = new WorkoutAdapter(this, R.layout.workout_item_layout, new ArrayList<>());
        workoutListView.setAdapter(workoutAdapter);

        loadData();
    }

    private void loadData() {
        new LoadClientInfoTask(
                restTemplate,
                clientId,
                client -> {
                    clientInfo = client;
                    updateFields();
                },
                code -> {
                    if (code == HttpStatus.FORBIDDEN) {
                        finish();
                    } else {
                        Toast.makeText(context, "Failed to load client info", Toast.LENGTH_SHORT).show();
                    }
                }
        ).loadFull(true).execute();
    }

    private void updateFields() {
        ViewUtils.setTextIfViewExists(findViewById(R.id.client_name), clientInfo.getFirstName());
        ViewUtils.setTextIfViewExists(findViewById(R.id.client_phone), clientInfo.getPhone());
        ViewUtils.setTextIfViewExists(findViewById(R.id.client_description), clientInfo.getDescription());
        if (clientInfo.getBirthDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("RU"));
            ViewUtils.setTextIfViewExists(findViewById(R.id.client_birth_date), dateFormat.format(clientInfo.getBirthDate()));
        }

        workoutAdapter.clear();
        workoutAdapter.addAll(clientInfo.getWorkoutInfoList());
    }
}
