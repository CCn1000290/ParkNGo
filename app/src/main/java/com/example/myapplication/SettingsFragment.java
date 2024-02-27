package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.pref_settings, rootKey);

        Preference helpFeedbackPreference = findPreference("help_feedback");
        if (helpFeedbackPreference != null) {
            helpFeedbackPreference.setOnPreferenceClickListener(preference -> {
                // start up a help and feedback fragment
                startActivity(new Intent(getActivity(), HelpFeedbackFragment.class));
                return true;
            });
        }
    }
}
