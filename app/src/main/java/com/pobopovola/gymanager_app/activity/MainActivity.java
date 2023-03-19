package com.pobopovola.gymanager_app.activity;

import static com.pobopovola.gymanager_app.activity.ClientViewActivity.CLIENT_ID_EXTRA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pobopovola.gymanager_app.CreditsHolder;
import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.adapter.ClientAdapter;
import com.pobopovola.gymanager_app.model.ClientInfo;
import com.pobopovola.gymanager_app.model.UserInfo;
import com.pobopovola.gymanager_app.tasks.LoadClientsTask;
import com.pobopovola.gymanager_app.tasks.LoadUserInfoTask;
import com.pobopovola.gymanager_app.utils.RestTemplateBuilder;


import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private final List<ClientInfo> clientInfoList = new ArrayList<>();
    private final RestTemplate restTemplate = RestTemplateBuilder.buildDefault();
    private Context context;
    private boolean authActivityStarted = false;

    private TextView userInfoTextView;

    private ClientAdapter clientArrayAdapter;
    private UserInfo currentUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        context = getApplicationContext();

        userInfoTextView = findViewById(R.id.current_user_info);
        ListView clientsListView = findViewById(R.id.clients_list);

        clientArrayAdapter = new ClientAdapter(this, R.layout.client_item_layout, clientInfoList);
        clientsListView.setAdapter(clientArrayAdapter);

        clientsListView.setOnItemClickListener((parent, view, position, id) -> {
            ClientInfo clientInfo = (ClientInfo) parent.getItemAtPosition(position);
            if (clientInfo != null) {
                Intent intent = new Intent(MainActivity.this, ClientViewActivity.class);
                intent.putExtra(CLIENT_ID_EXTRA, clientInfo.getId());
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCreditsAndValidate();
        authActivityStarted = false;
    }

    void loadData() {
        if (CreditsHolder.getToken() != null){
            new LoadUserInfoTask(
                    restTemplate,
                    userInfo -> {
                        currentUser = userInfo;
                        userInfoTextView.setText(currentUser.getFirstName());
                    },
                    code -> {
                        if (code == HttpStatus.FORBIDDEN) {
                            startAuthActivity();
                        }
                    }
            ).execute();

            new LoadClientsTask(
                    restTemplate,
                    clients -> {
                        clientInfoList.clear();
                        clientInfoList.addAll(clients);
                        clientArrayAdapter.notifyDataSetChanged();
                    },
                    code -> {
                        if (code == HttpStatus.FORBIDDEN) {
                            startAuthActivity();
                        } else {
                            Toast.makeText(context, "Can't load clients :(", Toast.LENGTH_SHORT).show();
                        }
                    }
            ).execute();
    }
    }

    private void loadCreditsAndValidate() {
        CreditsHolder.loadFromPreferences(context);

        if (CreditsHolder.getToken() != null) {
            loadData();
            return;
        }

        if (CreditsHolder.getCredits() == null) {
            startAuthActivity();
        } else {
            loadData();
        }
    }

    private synchronized void startAuthActivity() {
        if (authActivityStarted) {
            return;
        }
        authActivityStarted = true;
        Intent intent = new Intent(MainActivity.this, AuthViewActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CreditsHolder.saveToPreferences(context);
    }
}
