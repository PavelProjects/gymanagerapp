package com.pobopovola.gymanager_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.model.ExerciseResultInfo;
import com.pobopovola.gymanager_app.model.ExerciseTypeInfo;

import java.util.List;

public class ExerciseTypeAdapter extends ArrayAdapter<ExerciseTypeInfo> {
    private final LayoutInflater layoutInflater;
    private final int layoutResourceId;

    public ExerciseTypeAdapter(@NonNull Context context, int resource, List<ExerciseTypeInfo> objects) {
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

        setText(view, position);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = layoutInflater.inflate(layoutResourceId, null);
        } else {
            view = convertView;
        }

        setText(view, position);

        return view;
    }

    private void setText(View view, int position) {
        ExerciseTypeInfo exerciseTypeInfo = getItem(position);

        if (exerciseTypeInfo != null) {
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(exerciseTypeInfo.getCaption());
        }
    }
}
