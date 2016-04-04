package cs371m.taptaptap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView statsView;

    int score;
    int mistakes;
    int time;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        statsView = (TextView) findViewById(R.id.game_over_text_view);
        score = getIntent().getIntExtra("score", 0);
        mistakes = getIntent().getIntExtra("mistakes", 0);
        time = 0;
        statsView.setText("Final Score: " + score + "\nTotal Mistakes: " + mistakes + "\n Total Time: " + time);
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
        //need to fix
        Intent intent = new Intent(this, TapActivity.class);
        startActivity(intent);
    }

    public void returnToMainMenu(View view) {
        Intent intent = new Intent(this, MainLandingActivity.class);
        startActivity(intent);
    }
}
