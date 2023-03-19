package com.pobopovola.gymanager_app.utils;

import android.widget.TextView;

public class ViewUtils {
    public static void setIntIfViewExists(TextView textView, int value) {
        setTextIfViewExists(textView, String.valueOf(value));
    }
    public static void setTextIfViewExists(TextView textView, String value) {
        if (textView != null) {
            textView.setText(value);
        }
    }
}
