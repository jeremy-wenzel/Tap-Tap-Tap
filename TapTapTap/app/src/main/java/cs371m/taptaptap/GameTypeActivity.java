package cs371m.taptaptap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_type);
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

    public void startSingleWordGame(View view) {
        Intent intent = new Intent(this, TapActivity.class);
        startActivity(intent);
    }

    public void startMultiwordGame(View view) {
        Intent intent = new Intent(this, TapActivity.class);
        startActivity(intent);
    }

    public void startParagraphGame(View view) {
        Intent intent = new Intent(this, TapActivity.class);
        startActivity(intent);
    }
}
