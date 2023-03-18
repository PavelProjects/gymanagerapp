package com.pobopovola.gymanager_app.activity;

import static com.pobopovola.gymanager_app.activity.ClientViewActivity.CLIENT_ID_EXTRA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pobopovola.gymanager_app.CreditsHolder;
import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.adapter.ClientAdapter;
import com.pobopovola.gymanager_app.tasks.AuthUserTask;
import com.pobopovola.gymanager_app.model.Client;
import com.pobopovola.gymanager_app.model.UserInfo;
import com.pobopovola.gymanager_app.tasks.LoadClientsTask;
import com.pobopovola.gymanager_app.tasks.LoadUserInfoTask;


import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private final List<Client> clientList = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private Context context;

    private TextView userInfoTextView;

    private ClientAdapter clientArrayAdapter;
    private UserInfo currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        context = getApplicationContext();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        userInfoTextView = findViewById(R.id.current_user_info);
        ListView clientsListView = findViewById(R.id.clients_list);

        clientArrayAdapter = new ClientAdapter(this, R.layout.client_item_layout, clientList);
        clientsListView.setAdapter(clientArrayAdapter);

        clientsListView.setOnItemClickListener((parent, view, position, id) -> {
            Client client = (Client) parent.getItemAtPosition(position);
            if (client != null) {
                Intent intent = new Intent(MainActivity.this, ClientViewActivity.class);
                intent.putExtra(CLIENT_ID_EXTRA, client.getId());
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(view -> {
            CreditsHolder.setCredits(null);
            CreditsHolder.setToken(null);
            CreditsHolder.saveToPreferences(context);
            startAuthActivity();
        });

        loadCreditsAndValidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    void loadData() {
        if (CreditsHolder.getToken() != null){
            new LoadUserInfoTask(
                    restTemplate,
                    userInfo -> {
                        currentUser = userInfo;
                        userInfoTextView.setText(currentUser.getFirstName());
                    },
                    null
            ).execute();

            new LoadClientsTask(
                    restTemplate,
                    clients -> {
                        clientList.clear();
                        clientList.addAll(clients);
                        clientArrayAdapter.notifyDataSetChanged();
                    },
                    code -> {
                        Toast.makeText(context, "Can't load clients :(", Toast.LENGTH_SHORT).show();
                    }
            ).execute();
        }
    }

    private void loadCreditsAndValidate() {
        CreditsHolder.loadFromPreferences(context);

        if (CreditsHolder.getCredits() == null) {
            startAuthActivity();
        } else if (CreditsHolder.getToken() == null) {
            generateToken();
        } else {
            loadData();
        }
    }

    private void generateToken() {
        new AuthUserTask(
                restTemplate,
                token -> {
                    CreditsHolder.setToken(token);
                    CreditsHolder.saveToPreferences(context);
                },
                code -> {
                    if (code == HttpStatus.FORBIDDEN) {
                        startAuthActivity();
                    } else {
                        Toast.makeText(context, "Can't auth :(", Toast.LENGTH_LONG).show();
                    }
                }
        ).execute();
    }

    private void startAuthActivity() {
        Intent intent = new Intent(MainActivity.this, AuthViewActivity.class);
        startActivity(intent);
    }
}