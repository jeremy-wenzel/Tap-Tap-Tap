package cs371m.taptaptap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddWordsActivity extends AppCompatActivity {

    private final String GAMETYPE_EXTRA = "gameType";

    private TextView typeTextView;

    private final int MAX_SINGLE_WORD_CHARS = 15;
    private final int MAX_MULTIPLE_WORD_CHARS = 45;
    private final int MAX_PARAGRAPH_CHARS = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);

        Intent intent = getIntent();
        int gameType = intent.getIntExtra(GAMETYPE_EXTRA, -1);
        setUpTypeTextView(gameType);
    }

    /**
     * Sets up the TextView to tell the user what kind of gameType they have selected and
     * how many characters they can enter
     * @param gameType
     */
    private void setUpTypeTextView(int gameType) {
        StringBuilder type = new StringBuilder();
        type.append("For ");

        int maxChars = 0;

        switch (gameType) {
            case 0 :
                type.append(getResources().getString(R.string.single_word));
                maxChars = MAX_SINGLE_WORD_CHARS;
                break;
            case 1 :
                type.append(getResources().getString(R.string.multiple_words));
                maxChars = MAX_MULTIPLE_WORD_CHARS;
                break;
            case 2 :
                type.append(getResources().getString(R.string.paragraph));
                maxChars = MAX_PARAGRAPH_CHARS;
                break;
            default :
                throw new IllegalArgumentException("gameType not correct: " + gameType);
        }

        type.append(" the max characters you can enter is ");
        type.append(maxChars);

        typeTextView = (TextView) findViewById(R.id.add_words_type_text_view);
        typeTextView.setText(type.toString());
    }

    // Temporary
    public void saveButton(View view) {
        finish();
    }


}
