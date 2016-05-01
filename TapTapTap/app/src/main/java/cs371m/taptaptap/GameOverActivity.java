package cs371m.taptaptap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private static final String TAG = "GameOverActivity";

    private final String PHRASE_EXTRA = "Phrase";
    private final String NEW_GAME_EXTRA = "NewGame";


    TextView statsView;

    private int score;
    private int mistakes;
    private String time;
    private int gameType;
    private String phrase;

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
        Intent intent = getIntent();

        statsView = (TextView) findViewById(R.id.game_over_text_view);
        score = intent.getIntExtra("score", -1);
        mistakes = intent.getIntExtra("mistakes", -1);
        gameType = intent.getIntExtra("game type", -1);
        int seconds = intent.getIntExtra("seconds", -1);
        int minutes = intent.getIntExtra("minutes", -1);
        int numWordsTotal = intent.getIntExtra("numWordsTot", -1);
        int numWordsCorrect = intent.getIntExtra("numWordsCor", -1);
        double timeInMins = ((double)seconds)/60.0 + ((double)minutes);
        double gwam = Math.round(numWordsTotal/timeInMins);
        double cgwam = Math.round(numWordsCorrect/timeInMins);
        score = (int)Math.round(score*10.0*cgwam/gwam);
        phrase = getIntent().getStringExtra(PHRASE_EXTRA);
        if (phrase == null)
            Log.d(TAG, "phrase is null or length is zero");
        else if (phrase.length() == 0)
            Log.d(TAG, "length is zero");
        time = "" + minutes + ":";
        if (seconds < 10)
            time += "0";
        time += seconds;
        statsView.setText("Final Score: " + score + "\nTotal Mistakes: " + mistakes + "\nTotal Time: " + time
                + "\nGWAM (Total): " + gwam + "\nGWAM (Correct): " + cgwam);

        Database database = new Database(this);
        Log.d(TAG, "gametype = " + gameType);
        database.insertScore(score, gameType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_action:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.about_action:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

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
        intent.putExtra(NEW_GAME_EXTRA, false);
        intent.putExtra(PHRASE_EXTRA, phrase);
        startActivity(intent);
        finish();
    }

    public void returnToMainMenu(View view) {
        finish();
    }
}

