package cs371m.taptaptap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Rafik on 4/4/2016.
 */
public class HighScoreActivity extends AppCompatActivity {

    int highScores[] = new int[10];
    int score;
    TextView statsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        statsView = (TextView) findViewById(R.id.high_score_field);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        score = prefs.getInt("score", 0);
        statsView.setText("#1 - " + score);
    }
}