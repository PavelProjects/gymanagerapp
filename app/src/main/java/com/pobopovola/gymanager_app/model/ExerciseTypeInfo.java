package com.pobopovola.gymanager_app.model;

public class ExerciseTypeInfo {
    private String id;
    private String systemName;
    private String caption;
    private String description;
    private MeasureUnitsInfo measure;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MeasureUnitsInfo getMeasure() {
        return measure;
    }

    public void setMeasure(MeasureUnitsInfo measure) {
        this.measure = measure;
    }
}
