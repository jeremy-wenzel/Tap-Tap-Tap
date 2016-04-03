package cs371m.taptaptap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainLandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing);
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, GameTypeActivity.class);
        startActivity(intent);
    }

    public void highScores(View view) {
        Intent intent = new Intent(this, UnderConstruction.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(this, UnderConstruction.class);
        startActivity(intent);
    }
}
