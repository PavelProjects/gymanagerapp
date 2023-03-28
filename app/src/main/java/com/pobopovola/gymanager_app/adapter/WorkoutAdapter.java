package com.pobopovola.gymanager_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.model.WorkoutInfo;
import com.pobopovola.gymanager_app.utils.DateUtils;

import java.util.List;

public class WorkoutAdapter extends ArrayAdapter<WorkoutInfo> {
    private final LayoutInflater layoutInflater;
    private final int layoutResourceId;

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

        if (startDateView != null && workoutInfo.getStartDate() != null) {
            startDateView.setText(DateUtils.dateTimeToStringClient(workoutInfo.getStartDate()));
        }

        if (descriptionView != null) {
            descriptionView.setText(workoutInfo.getDescription());
        }

        return view;
    }
}
