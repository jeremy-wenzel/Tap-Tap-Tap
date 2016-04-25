package cs371m.taptaptap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private static final String TAG = "GameOverActivity";
    TextView statsView;

    int score;
    int mistakes;
    String time;
    int gameType;

    private final String EXTRA = "GameType";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        statsView = (TextView) findViewById(R.id.game_over_text_view);
        score = getIntent().getIntExtra("score", -1);
        mistakes = getIntent().getIntExtra("mistakes", -1);
        gameType = getIntent().getIntExtra("game type", -1);
        int seconds = getIntent().getIntExtra("seconds", -1);
        int minutes = getIntent().getIntExtra("minutes", -1);
        time = "" + minutes + ":";
        if (seconds < 10)
            time += "0";
        time += seconds;
        statsView.setText("Final Score: " + score + "\nTotal Mistakes: " + mistakes + "\nTotal Time: " + time);

        Database database = new Database(this);
        Log.d(TAG, "gametype = " + gameType);
        database.insertScore(score, gameType);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home)
            finish();
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void retry(View view) {
        Intent intent = new Intent(this, TapActivity.class);
        intent.putExtra(EXTRA, gameType);
        startActivity(intent);
        finish();
    }

    public void returnToMainMenu(View view) {
        finish();
    }
}

