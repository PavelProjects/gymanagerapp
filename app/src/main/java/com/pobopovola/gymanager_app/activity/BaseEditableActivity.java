package com.pobopovola.gymanager_app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pobopovola.gymanager_app.R;

public abstract class BaseEditableActivity extends AppCompatActivity {
    private final int layoutId;
    private boolean editable = false;

    private Button editButton;
    private Button saveButton;

    public BaseEditableActivity(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        setSupportActionBar(findViewById(R.id.edit_toolbar));

        initFields();
        editButton = findViewById(R.id.edit_button);
        saveButton = findViewById(R.id.save_button);

        editButton.setOnClickListener(view -> {
            editable = true;
            updateView();
        });

        saveButton.setOnClickListener(view -> {
            saveObject();
        });
    }

    protected boolean isEditable() {
        return editable;
    }

    protected synchronized void setEditable(boolean editable) {
        this.editable = editable;
        updateView();
    }

    protected void updateButtons() {
        if (editable) {
            saveButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
        } else {
            editButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.GONE);
        }
    }

    protected void updateView() {
        updateButtons();
        updateFields();
    }

    protected abstract void initFields();
    protected abstract void saveObject();
    // Use updateView() to update all view objects
    protected abstract void updateFields();
}
