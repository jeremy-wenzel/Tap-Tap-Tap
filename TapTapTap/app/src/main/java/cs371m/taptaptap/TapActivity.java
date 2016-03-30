package cs371m.taptaptap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class TapActivity extends AppCompatActivity {

    protected final String correctWords[] = {"My", "name", "is", "Rafik,", "Jeremy", "or", "Frank.", "Here", "is", "a", "sample", "passage", "for", "TapTapTap!"};

    //Temporary
    protected final int numWordsTotal = correctWords.length;

    protected int numWordsTyped = 0;

    protected String printingWords[] = new String[numWordsTotal];

    protected String userWords[] = new String[numWordsTotal];

    protected TextView paragraphView;
    protected EditText inputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        paragraphView = (TextView) findViewById(R.id.paragraph_view);
        inputField = (EditText) findViewById(R.id.input_view);

        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ( s.toString().contains(" ") ) {
                   userWords[numWordsTyped++] = s.toString().trim();
                    s.clear();
                }
                else {
                    // Store and color incomplete word
                    userWords[numWordsTyped] = s.toString();
                    printingWords[numWordsTyped] = WordColorer.colorWord(userWords[numWordsTyped], correctWords[numWordsTyped], false);
                }


                updateParagraphText("Begin: ");

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

    protected void updateParagraphText(String par) {
        paragraphView.setText(Html.fromHtml(par + printingWords[numWordsTyped]));
    }
}
/*
    protected String buildParagraph ( String words[] ) {
        StringBuilder toReturn = new StringBuilder(words[0]);

        for ( int i = 1 ; i < words.length; i++ )
            toReturn.append(" " + Html.fromHtml(words[i]));

        return toReturn.toString();
    }

    protected String buildParagraph ( String words[], int numWords ) {
        StringBuilder toReturn = new StringBuilder(Html.fromHtml(words[0]));

        for ( int i = 1 ; i < numWords; i++ )
            toReturn.append(" " + Html.fromHtml(words[i]));

        return toReturn.toString();

}/*
    @Override
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