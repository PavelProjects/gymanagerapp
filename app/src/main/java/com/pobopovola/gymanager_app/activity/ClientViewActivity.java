package com.pobopovola.gymanager_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pobopovola.gymanager_app.R;

public class ClientViewActivity extends AppCompatActivity {
    public static final String CLIENT_ID_EXTRA = "clientId";

    private TextView loginTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_view);

        loginTextView = findViewById(R.id.client_login);

        Intent intent = getIntent();
        String clientId = intent.getStringExtra(CLIENT_ID_EXTRA);

        loginTextView.setText(clientId);
    }
}
