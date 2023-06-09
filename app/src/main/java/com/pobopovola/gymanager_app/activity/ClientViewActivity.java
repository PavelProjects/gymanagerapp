package com.pobopovola.gymanager_app.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.adapter.WorkoutAdapter;
import com.pobopovola.gymanager_app.model.ClientInfo;
import com.pobopovola.gymanager_app.model.WorkoutInfo;
import com.pobopovola.gymanager_app.tasks.CreateUpdateClientTask;
import com.pobopovola.gymanager_app.tasks.LoadClientInfoTask;
import com.pobopovola.gymanager_app.utils.DateUtils;
import com.pobopovola.gymanager_app.utils.RestTemplateBuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class ClientViewActivity extends BaseEditableActivity {
    private final static String LOGGER_TAG = ClientViewActivity.class.getSimpleName();

    public static final String CLIENT_ID_EXTRA = "clientId";

    private final RestTemplate restTemplate = RestTemplateBuilder.buildDefault();
    private final Calendar birthdayCalendar = Calendar.getInstance();

    private Context context;
    private String clientId;
    private ClientInfo clientInfo;

    private EditText clientNameEditText;
    private EditText clientPhoneEditText;
    private EditText clientDescriptionEditText;
    private EditText clientBirthDateEditText;


    private WorkoutAdapter workoutAdapter;

    public ClientViewActivity() {
        super(R.layout.client_view);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        context = getBaseContext();
        clientId = getIntent().getStringExtra(CLIENT_ID_EXTRA);
        setEditable(StringUtils.isBlank(clientId));

        loadData();
    }

    @Override
    protected void initFields() {
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

        clientNameEditText = findViewById(R.id.client_name);
        clientPhoneEditText = findViewById(R.id.client_phone);
        clientDescriptionEditText = findViewById(R.id.client_description);
        clientBirthDateEditText = findViewById(R.id.client_birth_date);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            birthdayCalendar.set(Calendar.YEAR, year);
            birthdayCalendar.set(Calendar.MONTH,month);
            birthdayCalendar.set(Calendar.DAY_OF_MONTH,day);
            clientBirthDateEditText.setText(DateUtils.dateToStringClient(birthdayCalendar.getTime()));
        };

        clientBirthDateEditText.setOnClickListener(view -> new DatePickerDialog(
                ClientViewActivity.this,
                date,
                birthdayCalendar.get(Calendar.YEAR),
                birthdayCalendar.get(Calendar.MONTH),
                birthdayCalendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        findViewById(R.id.add_workout).setOnClickListener(view -> {
            startActivity(new Intent(this, WorkoutViewActivity.class));
        });
    }

    @Override
    protected void saveObject() {
        if (clientInfo == null) {
            clientInfo = new ClientInfo();
        }
        clientInfo.setFirstName(clientNameEditText.getText().toString());
        clientInfo.setPhone(clientPhoneEditText.getText().toString());
        clientInfo.setDescription(clientDescriptionEditText.getText().toString());
        clientInfo.setBirthday(birthdayCalendar.getTime());

        if (!clientInfo.validate()) {
            Toast.makeText(context, "Not all necessary fields are filed", Toast.LENGTH_LONG).show();
        }

        new CreateUpdateClientTask(
                restTemplate,
                result -> {
                    Toast.makeText(context, "Client " + (clientInfo.getId() == null ? "created" : "updated"), Toast.LENGTH_SHORT).show();
                    clientInfo = result;
                    setEditable(false);
                },
                code -> {
                    Log.e(LOGGER_TAG, "Client creation failed with code " + code.value());
                    Toast.makeText(context, "Failed to create client", Toast.LENGTH_SHORT).show();
                }
        ).setClientInfo(clientInfo).execute();
    }

    private void loadData() {
        if (StringUtils.isEmpty(clientId)) {
            getSupportActionBar().setTitle("Добавить клиента");
            updateView();
            return;
        }
        new LoadClientInfoTask(
            restTemplate,
            clientId,
            client -> {
                clientInfo = client;
                getSupportActionBar().setTitle(clientInfo.getFirstName());
                updateView();
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

    protected void updateFields() {
        clientNameEditText.setEnabled(isEditable());
        clientPhoneEditText.setEnabled(isEditable());
        clientDescriptionEditText.setEnabled(isEditable());
        clientBirthDateEditText.setEnabled(isEditable());

        if (clientInfo != null) {
            if (clientInfo.getBirthday() != null) {
                birthdayCalendar.setTime(clientInfo.getBirthday());
                clientBirthDateEditText.setText(DateUtils.dateToStringClient(clientInfo.getBirthday()));
            } else {
                Log.e(LOGGER_TAG, "Birth day is null");
            }

            clientNameEditText.setText(clientInfo.getFirstName());
            clientPhoneEditText.setText(clientInfo.getPhone());
            clientDescriptionEditText.setText(clientInfo.getDescription());

            workoutAdapter.clear();
            if (clientInfo.getWorkouts() != null) {
                workoutAdapter.addAll(clientInfo.getWorkouts());
            }
        }
    }
}
