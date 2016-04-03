package cs371m.taptaptap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TapActivity extends AppCompatActivity {

    private static final String TAG = "TapActivity";
    protected final String correctWords[] = {"My", "name", "is", "Rafik,", "Jeremy", "or", "Frank.", "Here", "is", "a", "sample", "passage", "for", "TapTapTap!"};

    //Temporary
    protected static int numWordsTotal;

    protected static int numWordsTyped = 0;

    protected static int score = 0;

    protected String printingWords[] = new String[numWordsTotal];

    protected String userWords[] = new String[numWordsTotal];

    List<WordNode> wordList = new ArrayList<WordNode>();

    protected TextView scoreView;
    protected TextView paragraphView;
    protected EditText inputField;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        Log.d(TAG, "In onCreate");

        // Setup views
        scoreView = (TextView) findViewById(R.id.score_view);
        paragraphView = (TextView) findViewById(R.id.paragraph_view);
        inputField = (EditText) findViewById(R.id.input_view);

        TextView Highlighter = (TextView) findViewById(R.id.paragraph_view);

        // Put paragraph into the wordList
        for (int i = 0; i < correctWords.length; i++)
            wordList.add(new WordNode(correctWords[i]));

        numWordsTotal = wordList.size();

        // Set the first value to appear on screen
        wordList.get(0).updateUserWord("", false);
        updateParagraphText();
        Spannable spanText = Spannable.Factory.getInstance().newSpannable(wordList.get(0).getCorrectWord());

        intent = new Intent(this, GameOverActivity.class);

        // input listener
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "In afterTextChanged");
                if (numWordsTyped >= numWordsTotal) {
                    gameOver();
                    return;
                }
                String str = s.toString();
                if ((str.contains(" ") || str.contains("\n"))) {
                    Log.d(TAG, "Found Space");
                    wordList.get(numWordsTyped++).updateUserWord(str.trim(), true);
                    s.clear();
                } else {
                    if (str.length() > 0) {
                        String usrWrd = wordList.get(numWordsTyped).getUserWord();
                        String corWrd = wordList.get(numWordsTyped).getCorrectWord();
                        int sLen = str.length();
                        int uLen = usrWrd.length();
                        if (sLen < uLen) {
                            Log.d(TAG, "User has deleted a letter");
                            score -= 1;
                        }
                        else if (sLen <= corWrd.length()
                                && str.charAt(sLen - 1) == corWrd.charAt(sLen - 1)
                                && !str.equals(usrWrd)) {
                            Log.d(TAG, "Incrementing Score");
                            Log.d(TAG, "str: " + str);
                            Log.d(TAG, "sLen: " + sLen);
                            if (uLen != 0)
                                score += 1;
                            else if (str.charAt(uLen) == corWrd.charAt(uLen))
                                score += 1;
                        }
                    }
                    else if (str.length() == 0 && wordList.get(numWordsTyped).isTyped()) {
                        --score;
                    }
                    // Store and color incomplete word
                    wordList.get(numWordsTyped).updateUserWord(str, false);
                }
                updateParagraphText();
                updateScore();
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

    public void gameOver() {
        startActivity(intent);
    }

    /*protected void updateFieldText() {

    }*/

    /**
     * Updates the paragraph view with the appropriate coloring and words
     */
    protected void updateParagraphText() {
        paragraphView.setText(Html.fromHtml(buildParagraph(wordList)), TextView.BufferType.SPANNABLE);
    }

    protected void updateScore() {
        scoreView.setText("Score: " + score);
    }

    /**
     * Builds the paragraph string which has coloring tags, that will be displayed to the user
     *
     * @param list The WordNode list that we are typing through
     * @return The paragrph with the coloring tags
     */
    protected String buildParagraph(List<WordNode> list) {

        StringBuilder toReturn = new StringBuilder();

        if (list.get(0).isTyped())
            toReturn.append(list.get(0).getColoredIWord());
        else
            return "";

        for (int i = 1; i < numWordsTotal; i++)
            if (list.get(i).isTyped())
                toReturn.append(" " + list.get(i).getColoredIWord());
            else
                toReturn.append(" " + list.get(i).getColoredCWord());
        toReturn.append(" ");

        return toReturn.toString();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if (e.getAction() == KeyEvent.ACTION_DOWN) {
        }

        if (e.getAction() == KeyEvent.ACTION_UP) {
            if (isValidDelete(e.getKeyCode())) {
                if (numWordsTyped > numWordsTotal) {
                    numWordsTyped--;
                } else if (numWordsTyped == numWordsTotal) {
                    inputField.setText(wordList.get(--numWordsTyped).getUserWord());
                    inputField.setSelection(wordList.get(numWordsTyped).getUserWord().length());
                    updateParagraphText();
                } else {
                    wordList.get(numWordsTyped--).resetWordNode();
                    inputField.setText(wordList.get(numWordsTyped).getUserWord());
                    inputField.setSelection(wordList.get(numWordsTyped).getUserWord().length());
                    updateParagraphText();
                }
                return true;
            }
        }

        return super.dispatchKeyEvent(e);
    }

    /**
     * Needed a method to validate if we can delete. Looks better than having in if statement
     * Need delete code, numWords greater than 0 and need current word to have a length of 0.
     *
     * @param keyCode KeyCode given by the event
     * @return true if delete key and numWordsType > 0 and the length of the currentword is 0
     */
    private boolean isValidDelete(int keyCode) {
        if (numWordsTyped < numWordsTotal)
            return keyCode == KeyEvent.KEYCODE_DEL &&
                    numWordsTyped > 0 &&
                    wordList.get(numWordsTyped).getUserWord().length() == 0;
        else
            return keyCode == KeyEvent.KEYCODE_DEL && numWordsTyped > 0;
    }
}
