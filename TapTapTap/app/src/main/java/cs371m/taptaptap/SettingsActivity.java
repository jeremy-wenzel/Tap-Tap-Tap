package cs371m.taptaptap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Rafik on 4/4/2016.
 */
public class SettingsActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }


//    public void showIntroMessage(View view) {
//        SharedPreferences settings = getSharedPreferences("prefs", 0);
//        SharedPreferences.Editor editor = settings.edit();
//
//        editor.putString("skipMessage", "NOT checked");
//        editor.commit();
//    }
}
