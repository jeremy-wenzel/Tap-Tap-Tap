package cs371m.taptaptap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddWordsGameTypeActivity extends AppCompatActivity {

    private final String GAMETYPE_EXTRA = "gameType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words_game_type);
    }

    public void addSingleWord(View view) {
        sendIntent(0);
    }

    public void addMultipleWords(View view) {
        sendIntent(1);
    }

    public void addParagraph(View view) {
        sendIntent(2);
    }

    private void sendIntent(int gameType) {
        Intent intent = new Intent(this, AddWordsActivity.class);
        intent.putExtra(GAMETYPE_EXTRA, gameType);
        startActivity(intent);
    }
}
