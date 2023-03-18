package com.pobopovola.gymanager_app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientInfo {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String avatarURL;
    private Date birthDate;
    private String description;
    private List<WorkoutInfo> workoutInfoList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public List<WorkoutInfo> getWorkoutInfoList() {
        return workoutInfoList;
    }

    public void setWorkoutInfoList(List<WorkoutInfo> workoutInfoList) {
        this.workoutInfoList = workoutInfoList;
    }

    public void addWorkoutInfo(WorkoutInfo workoutInfo) {
        this.workoutInfoList.add(workoutInfo);
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
