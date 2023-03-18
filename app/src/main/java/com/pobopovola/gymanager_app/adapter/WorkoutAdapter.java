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
import com.pobopovola.gymanager_app.model.WorkoutInfo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class WorkoutAdapter extends ArrayAdapter<WorkoutInfo> {
    private static final String LOGGER_TAG = WorkoutAdapter.class.getSimpleName();

    private LayoutInflater layoutInflater;
    private int layoutResourceId;

    public WorkoutAdapter(@NonNull Context context, int resource, @NonNull List<WorkoutInfo> workouts) {
        super(context, resource, workouts);

        layoutResourceId = resource;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WorkoutInfo workoutInfo = getItem(position);
        View view;

        if (convertView == null) {
            view = layoutInflater.inflate(layoutResourceId, null);
        } else {
            view = convertView;
        }

        TextView startDateView = view.findViewById(R.id.workout_start_date);
        TextView descriptionView = view.findViewById(R.id.workout_description);

        if (startDateView != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.forLanguageTag("RU"));
            startDateView.setText(dateFormat.format(workoutInfo.getStartDate()));
        }

        if (descriptionView != null) {
            descriptionView.setText(workoutInfo.getDescription());
        }

        return view;
    }
}
