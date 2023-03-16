package com.pobopovola.gymanager_app.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.model.Client;


import java.util.ArrayList;
import java.util.List;

public class ClientsViewActivity extends AppCompatActivity {
    private List<Client> clientList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clients_view);
    }

}
