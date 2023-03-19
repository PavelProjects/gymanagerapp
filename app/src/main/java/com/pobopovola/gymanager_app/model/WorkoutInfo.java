package com.pobopovola.gymanager_app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutInfo {
    private String id;
    private LocalDateTime startDate;
    private UserInfo trainer;
    private String description;
    private List<ExerciseInfo> exercises = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public UserInfo getTrainer() {
        return trainer;
    }

    public void setTrainer(UserInfo trainer) {
        this.trainer = trainer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExerciseInfo> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseInfo> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(ExerciseInfo exerciseInfo) {
        this.exercises.add(exerciseInfo);
    }
}
