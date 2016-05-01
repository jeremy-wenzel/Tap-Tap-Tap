package cs371m.taptaptap;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Rafik on 4/4/2016.
 */
public class SettingsActivity extends PreferenceActivity {


    private CheckBox sound;
    private Button Clickhere;

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
        final String textSize = prefs.getString("text_size", getResources().getString(R.string.medium_size));

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


        sound = (CheckBox) findViewById(R.id.sound);

//        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//        @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //Log.d("CHECK", "CHECKED");
//            }
//        });


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

        Preference resetDictionaryButton = (Preference)findPreference(getString(R.string.reset_game_dictionary_key));
        resetDictionaryButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Database database = new Database(SettingsActivity.this.getApplicationContext());
                database.resetAllPhrases();
                return true;
            }
        });

        Preference resetHighScoresButton = (Preference)findPreference(getString(R.string.reset_high_score_key));
        resetHighScoresButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Database database = new Database (SettingsActivity.this.getApplicationContext());
                database.resetHighScores();
                return true;
            }
        });
    }
}
