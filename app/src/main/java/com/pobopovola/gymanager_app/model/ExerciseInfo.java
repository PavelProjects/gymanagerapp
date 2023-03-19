package com.pobopovola.gymanager_app.model;

import java.util.ArrayList;
import java.util.List;

public class ExerciseInfo {
    private String id;
    private ExerciseTypeInfo exerciseType;
    private String note;
    private List<ExerciseResultInfo> results = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExerciseTypeInfo getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseTypeInfo exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ExerciseResultInfo> getResults() {
        return results;
    }

    public void setResults(List<ExerciseResultInfo> results) {
        this.results = results;
    }

    public void addResult(ExerciseResultInfo resultInfo) {
        this.results.add(resultInfo);
    }
}
