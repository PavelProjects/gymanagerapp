package com.pobopovola.gymanager_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.model.ExerciseResultInfo;

import java.util.List;

public class ExerciseResultAdapter extends ArrayAdapter<ExerciseResultInfo> {
    private final LayoutInflater layoutInflater;
    private final int layoutResourceId;

    public ExerciseResultAdapter(@NonNull Context context, int resource, List<ExerciseResultInfo> objects) {
        super(context, resource, objects);

        this.layoutResourceId = resource;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = layoutInflater.inflate(layoutResourceId, null);
        } else {
            view = convertView;
        }

        ExerciseResultInfo resultInfo = getItem(position);
        if (resultInfo != null) {
            EditText weight = view.findViewById(R.id.exercise_result_weight);
            EditText repeats = view.findViewById(R.id.exercise_result_repeats);
            TextView number = view.findViewById(R.id.exercise_result_number);
            Button button = view.findViewById(R.id.exercise_result_remove);

            weight.setText(String.valueOf(resultInfo.getWeight()));
            repeats.setText(String.valueOf(resultInfo.getRepeats()));
            number.setText(String.valueOf(position + 1));
            button.setOnClickListener(v -> {
                remove(resultInfo);
            });
        }

        return view;
    }
}
