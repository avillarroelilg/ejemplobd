package com.example.ejemplodb;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.xml.activity_preferences);
        addPreferencesFromResource(R.xml.preference);
    }
}
