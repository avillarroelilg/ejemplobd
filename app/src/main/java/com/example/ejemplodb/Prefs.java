package com.example.ejemplodb;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;

public class Prefs extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            int res = getActivity().getResources().getIdentifier(getArguments().getString("resource"), "xml", getActivity().getPackageName());
            addPreferencesFromResource(res);

            EditTextPreference ip_edt = new EditTextPreference(this.getContext());
            ip_edt.setKey("test");
            ip_edt.setDefaultValue("10.0.2.2");
            ip_edt.setTitle("IP DB");
        }
}
