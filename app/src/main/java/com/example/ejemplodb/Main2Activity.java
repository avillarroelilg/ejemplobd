package com.example.ejemplodb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.TypedValue;

public class Main2Activity extends PreferenceActivity  {

    private int n=1;
// esto hay que hacerlo una classe para crear tantos como querramos.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        addPreferencesFromResource(R.xml.template);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        n = Integer.parseInt(sharedPreferences.getString("list_preference","1"));

        PreferenceScreen preferenceScreen = this.getPreferenceScreen();

// create preferences manually
        PreferenceCategory preferenceCategory = new PreferenceCategory(preferenceScreen.getContext());
        preferenceCategory.setTitle("template");
// do anything you want with the preferencecategory here
        preferenceScreen.addPreference(preferenceCategory);

// do anything you want with the preferencey here

        EditTextPreference ip_edt = new EditTextPreference(preferenceScreen.getContext());
        ip_edt.setKey("url_db" + n);
        ip_edt.setDefaultValue("10.0.2.2");
        ip_edt.setTitle("IP DB");

        EditTextPreference name_db = new EditTextPreference(preferenceScreen.getContext());
        name_db.setKey("name_db" + n);
        name_db.setDefaultValue("ejemplos");
        name_db.setTitle("nombre db");

        EditTextPreference user = new EditTextPreference(preferenceScreen.getContext());
        user.setKey("user_name" + n);
        user.setDefaultValue("boss");
        user.setTitle("usuario");

        EditTextPreference pass = new EditTextPreference(preferenceScreen.getContext());
        pass.setKey("user_pass" + n);
        pass.setDefaultValue("123456");
        pass.setTitle("contraseña");

       // preferenceCategory.addPreference(preference);
        preferenceCategory.addPreference(ip_edt);
        preferenceCategory.addPreference(name_db);
        preferenceCategory.addPreference(user);
        preferenceCategory.addPreference(pass);
    }
}
