package cs371m.taptaptap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TapActivity extends AppCompatActivity {

    private static final String TAG = "TapActivity";

    //Temporary
    protected static int numWordsTotal;

    protected static int numWordsTyped;

    ScoreSystem score = new ScoreSystem();

    List<WordNode> wordList = new ArrayList<WordNode>();

    protected TextView scoreView;
    protected TextView paragraphView;
    protected EditText inputField;

    private Intent intent;

    private int gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);



        // Get intent data and set up game state
        intent = getIntent();

        int value = intent.getIntExtra("GameType", -1);
        gameType = value;
        setUpGame(value);

        // Setup views
        scoreView = (TextView) findViewById(R.id.score_view);
        paragraphView = (TextView) findViewById(R.id.paragraph_view);
        inputField = (EditText) findViewById(R.id.input_view);


        // Set the first value to appear on screen
        wordList.get(0).updateUserWord("", false);
        updateParagraphText();

        score.set_upper_limit(wordList.size());

//        intent = new Intent(this, GameOverActivity.class);

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
//                Log.d(TAG, "In afterTextChanged");
                if (numWordsTyped >= numWordsTotal) {
                    gameOver();
                    return;
                }
                String str = s.toString();
                if ((str.contains(" ") || str.contains("\n"))) {

                    if(!str.equals(wordList.get(numWordsTyped).getCorrectWord()))
                        score.add_mistake();
                    wordList.get(numWordsTyped++).updateUserWord(str.trim(), true);
                    s.clear();
                }
                else {
                    if (str.length() > 0) {
                        String usrWrd = wordList.get(numWordsTyped).getUserWord();
                        String corWrd = wordList.get(numWordsTyped).getCorrectWord();
                        int sLen = str.length();
                        int uLen = usrWrd.length();
                        if (sLen < uLen) {

                            score.subtract_score();
                        }
                        else if (sLen <= corWrd.length()
                                && str.charAt(sLen - 1) == corWrd.charAt(sLen - 1)
                                && !str.equals(usrWrd)) {

                            if (uLen != 0 || str.charAt(uLen) == corWrd.charAt(uLen))
                                score.add_score();
                        }
                        else if(sLen > corWrd.length()){
                            score.add_mistake();
                        }
                        else if((str.charAt(sLen - 1) != corWrd.charAt(sLen - 1)) && !str.equals(usrWrd)){
                            score.add_mistake();
                        }

                    }
                    else if (str.length() == 0 && wordList.get(numWordsTyped).isTyped()) {
                        score.subtract_score();
                    }

                    // Store and color incomplete word
                    wordList.get(numWordsTyped).updateUserWord(str, false);
                    if((numWordsTyped == numWordsTotal-1) && str.equals(wordList.get(numWordsTyped).getCorrectWord())){
                        gameOver();
                    }
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
        for(int i = 0; i < wordList.size(); i++){
            if(wordList.get(i).isCorrect())
                score.add_word_score(wordList.get(i).getCorrectWord().length());
        }

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", score.get_score());
        editor.commit();

        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("score", score.get_score());
        intent.putExtra("mistakes", score.get_mistakes());
        intent.putExtra("game type", gameType);
        startActivity(intent);
    }

    /*protected void updateFieldText() {

    }*/

    /**
     * Checks to make sure that we have a good game type and then begins to build the game state
     *
     * @param gameType The game type we want to play. 0 for single word, 1 for multiword, 2 for paragraph
     */
    public void setUpGame(int gameType) {
        switch (gameType) {
            case 0:
                buildCorrectWordList(R.raw.single_word);
                break;
            case 1:
                buildCorrectWordList(R.raw.multiple_words);
                break;
            case 2:
                buildCorrectWordList(R.raw.paragraph);
                break;
            default:
                throw new IllegalArgumentException("Did not get correct gameType");
        }
    }

    /**
     * Updates the paragraph view with the appropriate coloring and words
     */
    protected void updateParagraphText() {
        paragraphView.setText(Html.fromHtml(buildParagraph(wordList)), TextView.BufferType.SPANNABLE);
    }

    protected void updateScore() {
        scoreView.setText("Score: " + score.get_score());
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

    /**
     * Sets up the game state given the Resource id of a file. More specifically, goes
     * through the file, gets all the words, and adds them to the word list. Then sets
     * the number of words total to the size of the word list.
     *
     * @param file Resource file id of file to be read
     */
    private void buildCorrectWordList(int file) {
        InputStream input = getResources().openRawResource(file);
        Scanner scan = new Scanner(input);

        while (scan.hasNext()) {
            String word = scan.next();
            wordList.add(new WordNode(word));
        }

        numWordsTotal = wordList.size();
        numWordsTyped = 0;
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
