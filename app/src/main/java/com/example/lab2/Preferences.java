package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

public class Preferences extends AppCompatActivity {

    Switch location;
    Switch camera;
    TextView firstName;
    TextView lastName;

    private final String PREFS = "PREFS";
    private final String LOCATION = "LOCATION";
    private final String CAMERA = "CAMERA";
    private final String FIRST_NAME = "FIRST_NAME";
    private final String LAST_NAME = "LAST_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        location = findViewById(R.id.location);
        camera = findViewById(R.id.camera);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);

        this.loadPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.savePreferences();
    }

    private void savePreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(LOCATION, location.isChecked());
        editor.putBoolean(CAMERA, camera.isChecked());
        editor.putString(FIRST_NAME, firstName.getText().toString());
        editor.putString(LAST_NAME, lastName.getText().toString());

        editor.apply();
    }

    private void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        location.setChecked(preferences.getBoolean(LOCATION, false));
        camera.setChecked(preferences.getBoolean(CAMERA, false));
        firstName.setText(preferences.getString(FIRST_NAME, firstName.getText().toString()));
        lastName.setText(preferences.getString(LAST_NAME, lastName.getText().toString()));
    }
}
