package com.pobopovola.gymanager_app.adapter;

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
import com.pobopovola.gymanager_app.model.ClientInfo;

import java.util.List;

public class ClientAdapter extends ArrayAdapter<ClientInfo> {
    private static final String LOG_TAG = ClientAdapter.class.getSimpleName();

    private LayoutInflater layoutInflater;
    private int layoutResourceId;


    public ClientAdapter(@NonNull Context context, int resource, @NonNull List<ClientInfo> clientInfoList) {
        super(context, resource, clientInfoList);

        this.layoutResourceId = resource;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ClientInfo clientInfo = getItem(position);
        View view;

        if (convertView == null) {
            view = layoutInflater.inflate(layoutResourceId, null);
        } else {
            view = convertView;
        }

        TextView idView = view.findViewById(R.id.client_list_id);
        TextView nameView = view.findViewById(R.id.client_list_name);

        if (idView != null) {
            idView.setText(clientInfo.getId());
        }

        if (nameView != null) {
            nameView.setText(clientInfo.getFirstName());
        }
        return view;
    }
}
