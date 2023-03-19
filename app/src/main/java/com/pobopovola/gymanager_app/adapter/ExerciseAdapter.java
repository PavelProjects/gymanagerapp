package com.pobopovola.gymanager_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pobopovola.gymanager_app.R;
import com.pobopovola.gymanager_app.model.ExerciseInfo;
import com.pobopovola.gymanager_app.model.ExerciseResultInfo;
import com.pobopovola.gymanager_app.utils.ViewUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExerciseAdapter extends ArrayAdapter<ExerciseInfo> {
    private final LayoutInflater layoutInflater;
    private final int layoutResourceId;

    public ExerciseAdapter(@NonNull Context context, int resource, List<ExerciseInfo> exerciseInfos) {
        super(context, resource, exerciseInfos);

        this.layoutResourceId = resource;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ExerciseInfo exerciseInfo = getItem(position);
        View view;

        if (convertView == null) {
            view = layoutInflater.inflate(layoutResourceId, null);
        } else {
            view = convertView;
        }

        ViewUtils.setTextIfViewExists(view.findViewById(R.id.exercise_caption), exerciseInfo.getExerciseType().getCaption());
        ViewUtils.setIntIfViewExists(view.findViewById(R.id.exercise_results_count), exerciseInfo.getResults().size() + 1);

        if (exerciseInfo.getResults().size() > 0) {
            List<ExerciseResultInfo> resultsSorted =  exerciseInfo.getResults()
                    .stream()
                    .sorted(Comparator.comparingInt(ExerciseResultInfo::getResult))
                    .collect(Collectors.toList());

            ViewUtils.setIntIfViewExists(view.findViewById(R.id.exercise_best_result), resultsSorted.get(resultsSorted.size() - 1).getResult());
        }
        ViewUtils.setTextIfViewExists(view.findViewById(R.id.exercise_note), exerciseInfo.getNote());

        return view;
    }
}
