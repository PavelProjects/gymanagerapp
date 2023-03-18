package com.pobopovola.gymanager_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.model.Client;

import java.util.List;

public class ClientAdapter extends ArrayAdapter<Client> {
    private static final String LOG_TAG = ClientAdapter.class.getSimpleName();

    private LayoutInflater layoutInflater;
    private int layoutResourceId;


    public ClientAdapter(@NonNull Context context, int resource, @NonNull List<Client> clientList) {
        super(context, resource, clientList);

        this.layoutResourceId = resource;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            Client client = getItem(position);
            View view;

            if (convertView == null) {
                view = layoutInflater.inflate(layoutResourceId, null);
            } else {
                view = convertView;
            }

            TextView loginView = view.findViewById(R.id.client_list_login);
            TextView nameView = view.findViewById(R.id.client_list_name);

            if (loginView != null) {
                loginView.setText(client.getLogin());
            }

            if (nameView != null) {
                nameView.setText(client.getFirstName());
            }

            return view;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Failed to create view", ex);
            return null;
        }
    }
}
