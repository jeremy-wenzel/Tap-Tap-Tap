package cs371m.taptaptap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class TapActivity extends AppCompatActivity {

    protected final int colorStartIndex = 14;
    protected final String redBegin = "<font color='#FF0000'>";
    protected final String greenBegin = "<font color='#00FF00'>";
    protected final String blackBegin = "<font color='#000000'>";
    protected final String endTag = "</font>";

    protected final String correctWords[] = { "My", "name", "is", "Rafik,", "Jeremy", "or", "Frank.", "Here", "is", "a", "sample", "passage", "for", "TapTapTap!" };

    protected final int numWordsTotal = correctWords.length;

    protected int numWordsTyped = 0;
    protected String printingWords[] = new String[numWordsTotal];

    protected String userWords[] = new String[numWordsTotal];

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        int index = 4;
        userWords[0] = "My";
        userWords[1] = "name";
        userWords[2] = "is";
        userWords[index-1] = "Rafik,";
        userWords[index] = "Jexery";
        numWordsTyped = 5;

        for ( int i = 0 ; i < numWordsTyped ; i++ )
            printingWords[i] = colorLetters(userWords[i], correctWords[i]);

        TextView textView = (TextView) findViewById(R.id.paragraph_view);
        textView.setText(Html.fromHtml(buildParagraph(printingWords)));
    }

    protected String buildParagraph ( String words[] ) {
        StringBuilder toReturn = new StringBuilder(words[0]);

        for ( int i = 1 ; i < words.length; i++ )
            toReturn.append(" " + words[i]);

        return toReturn.toString();
    }

    protected String buildParagraph ( String words[], int numWords ) {
        StringBuilder toReturn = new StringBuilder(words[0]);

        for ( int i = 1 ; i < numWords; i++ )
            toReturn.append(" " + words[i]);

        return toReturn.toString();
    }

    /*

        Consider cases where the number of letters in the typed word != the number of letters in the correct word

     */
    protected String colorWord ( String word, String color ) {
        String toReturn = color + word + endTag;
        return toReturn;
    }

    protected String colorLetters ( String userWord, String correctWord ) {
        StringBuilder toReturn = new StringBuilder();

        int i = 0;
        for ( ; i < userWord.length() ; ++i )
            if (userWord.charAt(i) == correctWord.charAt(i))
                toReturn.append(greenBegin + userWord.charAt(i) + endTag);
            else
                toReturn.append(redBegin + userWord.charAt(i) + endTag);

        for ( ; i < correctWord.length() ; ++i )
            toReturn.append(blackBegin + correctWord.charAt(i) + endTag);

        return toReturn.toString();
    }
}
