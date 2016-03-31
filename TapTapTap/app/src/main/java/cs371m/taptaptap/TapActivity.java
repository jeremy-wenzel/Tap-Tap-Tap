package cs371m.taptaptap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TapActivity extends AppCompatActivity {

    protected final String correctWords[] = {"My", "name", "is", "Rafik,", "Jeremy", "or", "Frank.", "Here", "is", "a", "sample", "passage", "for", "TapTapTap!"};

    //Temporary
    protected final int numWordsTotal = correctWords.length;

    protected int numWordsTyped = 0;

    protected String printingWords[] = new String[numWordsTotal];

    protected String userWords[] = new String[numWordsTotal];

    List<WordNode> wordList = new ArrayList<WordNode>();

    protected TextView paragraphView;
    protected EditText inputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        paragraphView = (TextView) findViewById(R.id.paragraph_view);
        inputField = (EditText) findViewById(R.id.input_view);

        for ( int i = 0 ; i < correctWords.length ; i++ )
            wordList.add(new WordNode(correctWords[i]));

        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s2 = s.toString();
                if (s.toString().contains(" ")) {
                    wordList.get(numWordsTyped++).updateUserWord(s.toString().trim(), true);
                    s.clear();
                }
                else {
                    // Store and color incomplete word
                    wordList.get(numWordsTyped).updateUserWord(s2, false);
                }
                updateParagraphText();
            }
        });
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

    protected void updateFieldText() {

    }

    protected void updateParagraphText() {
        paragraphView.setText(Html.fromHtml(buildParagraph(wordList)));
    }

    protected String buildParagraph ( List<WordNode> list ) {

        StringBuilder toReturn = new StringBuilder();

        if ( list.get(0).isTyped() )
            toReturn.append(list.get(0).getColoredIWord());
        else
            return "";

        for ( int i = 1 ; i < list.size() ; i++ )
            if ( list.get(i).isTyped() )
                toReturn.append(" " + list.get(i).getColoredIWord());

        return toReturn.toString();
    }


    /*@Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        userWords[numWordsTyped] = inputField.getText().toString();
        inputField.setText("WHAT??");
       /* if (userWords[numWordsTyped].length() == 0)
            return true;/

        // Color completed words
        for (int i = 0; i < numWordsTyped - 1; i++)
            printingWords[i] = WordColorer.colorWord(userWords[i], correctWords[i], true);

        // Color incomplete words
        printingWords[numWordsTyped] = WordColorer.colorWord(userWords[numWordsTyped], correctWords[numWordsTyped], false);

        updateDisplayedText();

        return true;
    }*/
}