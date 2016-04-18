package cs371m.taptaptap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private static final String TAG = "GameOverActivity";
    TextView statsView;

    int score;
    int mistakes;
    int time;
    int gameType;

    private final String EXTRA = "GameType";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        statsView = (TextView) findViewById(R.id.game_over_text_view);
        score = getIntent().getIntExtra("score", -1);
        mistakes = getIntent().getIntExtra("mistakes", -1);
        gameType = getIntent().getIntExtra("game type", -1);
        time = 0;
        statsView.setText("Final Score: " + score + "\nTotal Mistakes: " + mistakes + "\n Total Time: " + time);

        Database database = new Database(this);
        database.insertScore(score, gameType);
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

    public void startNewGame(View view) {
        Intent intent = new Intent(this, GameTypeActivity.class);
        startActivity(intent);
    }

    public void retry(View view) {
        Intent intent = new Intent(this, TapActivity.class);
        intent.putExtra(EXTRA, gameType);
        startActivity(intent);
    }

    public void returnToMainMenu(View view) {
        Intent intent = new Intent(this, MainLandingActivity.class);
        startActivity(intent);
    }
}
