package cs371m.taptaptap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class TapActivity extends AppCompatActivity {

    protected final String correctWords[] = { "My", "name", "is", "Rafik,", "Jeremy", "or", "Frank.", "Here", "is", "a", "sample", "passage", "for", "TapTapTap!" };

    protected final int numWordsTotal = correctWords.length;

    protected int numWordsTyped = 0;
    protected String printingWords[] = new String[numWordsTotal];

    protected String userWords[] = new String[numWordsTotal];

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        userWords[0] = "My";
        userWords[1] = "name";
        userWords[2] = "is";
        userWords[3] = "Rafik,";
        userWords[4] = "Jexery";
        userWords[5] = "allibaba";
        userWords[6] = "fRank";
        numWordsTyped = 7;

        final String correctWords[] = { "My", "name", "is", "Rafik,", "Jeremy", "or", "Frank.", "Here", "is", "a", "sample", "passage", "for", "TapTapTap!" };


        for ( int i = 0 ; i < numWordsTyped - 1; i++ )
            printingWords[i] = WordColorer.colorWord(userWords[i], correctWords[i], true);

        printingWords[6] = WordColorer.colorWord(userWords[6], correctWords[6], false);

        TextView textView = (TextView) findViewById(R.id.paragraph_view);
        textView.setText(Html.fromHtml(buildParagraph(printingWords)));
    }

    @Override
    protected void onResume ( ) {
        super.onResume();

    }

    @Override
    protected void onPause ( ) {
        super.onPause();

    }

    @Override
    protected void onStop ( ) {
        super.onStop();

    }

    @Override
    protected void onDestroy ( ) {
        super.onDestroy();

    }


    public String buildParagraph ( String words[] ) {
        StringBuilder toReturn = new StringBuilder(words[0]);

        for ( int i = 1 ; i < words.length; i++ )
            toReturn.append(" " + words[i]);

        return toReturn.toString();
    }

    public String buildParagraph ( String words[], int numWords ) {
        StringBuilder toReturn = new StringBuilder(words[0]);

        for ( int i = 1 ; i < numWords; i++ )
            toReturn.append(" " + words[i]);

        return toReturn.toString();
    }

}
