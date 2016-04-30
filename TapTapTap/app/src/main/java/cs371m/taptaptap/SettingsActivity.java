package cs371m.taptaptap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

/**
 * Created by Rafik on 4/4/2016.
 */
public class SettingsActivity extends PreferenceActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName("prefs");
        addPreferencesFromResource(R.xml.preferences);

        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final ListPreference difficultyLevelPref = (ListPreference) findPreference("difficulty_level");
        final String difficulty = prefs.getString("difficulty_level",
                getResources().getString(R.string.easy));
        difficultyLevelPref.setSummary((CharSequence) difficulty);

        difficultyLevelPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                difficultyLevelPref.setSummary((CharSequence) newValue);

                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("difficulty_level", newValue.toString());
                ed.apply();
                return true;
            }
        });

        final ListPreference textSizePref = (ListPreference) findPreference("text_size");
        final String textSize = prefs.getString("text_size",
                getResources().getString(R.string.medium_size));
        textSizePref.setSummary((CharSequence) textSize);

        textSizePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                textSizePref.setSummary((CharSequence) newValue);

                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("text_size", newValue.toString());
                ed.apply();
                return true;
            }
        });

        final ListPreference orientationPref = (ListPreference) findPreference("orientation_pref");
        final String orientationString = prefs.getString("orientation_pref",
                getResources().getString(R.string.portrait));
        orientationPref.setSummary((CharSequence) orientationString);

        orientationPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                orientationPref.setSummary((CharSequence) newValue);
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("orientation_pref", newValue.toString());
                ed.apply();
                return true;
            }
        });
    }

}
