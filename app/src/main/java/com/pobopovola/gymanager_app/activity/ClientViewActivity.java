package com.pobopovola.gymanager_app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.adapter.WorkoutAdapter;
import com.pobopovola.gymanager_app.model.ClientInfo;
import com.pobopovola.gymanager_app.model.WorkoutInfo;
import com.pobopovola.gymanager_app.tasks.LoadClientInfoTask;
import com.pobopovola.gymanager_app.utils.DateUtils;
import com.pobopovola.gymanager_app.utils.RestTemplateBuilder;
import com.pobopovola.gymanager_app.utils.ViewUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class ClientViewActivity extends AppCompatActivity {
    private final static String LOGGER_TAG = ClientViewActivity.class.getSimpleName();

    public static final String CLIENT_ID_EXTRA = "clientId";

    private final RestTemplate restTemplate = RestTemplateBuilder.buildDefault();

    private Context context;
    private String clientId;
    private ClientInfo clientInfo;

    private WorkoutAdapter workoutAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_view);
        context = getBaseContext();

        clientId = getIntent().getStringExtra(CLIENT_ID_EXTRA);
        if (StringUtils.isEmpty(clientId)) {
            finish();
        }

        ListView workoutListView = findViewById(R.id.client_workouts);
        workoutAdapter = new WorkoutAdapter(this, R.layout.workout_item_layout, new ArrayList<>());
        workoutListView.setAdapter(workoutAdapter);
        workoutListView.setOnItemClickListener((parent, view, position, id) -> {
            WorkoutInfo workoutInfo = (WorkoutInfo) parent.getItemAtPosition(position);
            if (workoutInfo != null) {
                Intent intent = new Intent(this, WorkoutViewActivity.class);
                intent.putExtra(WorkoutViewActivity.WORKOUT_ID_EXTRA, workoutInfo.getId());
                startActivity(intent);
            }
        });

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
        ).execute();
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
        workoutAdapter.addAll(clientInfo.getWorkouts());
    }
}
