package cs371m.taptaptap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class TapActivity extends AppCompatActivity {

    private static final String TAG = "TapActivity";

    private final String GAME_TYPE_EXTRA = "GameType";
    private final String NEW_GAME_EXTRA = "NewGame";
    private final String PHRASE_EXTRA = "Phrase";

    protected static int numWordsTotal;
    protected static int numWordsTyped;

    ScoreSystem score = new ScoreSystem();

    List<WordNode> wordList = new ArrayList<WordNode>();

    protected TextView scoreView;
    protected TextView paragraphView;
    protected TextView timerView;
    protected EditText inputField;

    private Intent intent;

    private int gameType;
    private String mPhrase;

    private Timer timer;
    private int minutes = 0;
    private int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout stuff and home button
        setContentView(R.layout.activity_tap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        String textSize = settings.getString("text_size", "Medium");


        // Setup views
        scoreView = (TextView) findViewById(R.id.score_view);
        paragraphView = (TextView) findViewById(R.id.paragraph_view);
        inputField = (EditText) findViewById(R.id.input_view);
        timerView = (TextView) findViewById(R.id.timer_view);

        setUpTextSize(textSize);

        // Get intent data and set up game state
        intent = getIntent();
        gameType = intent.getIntExtra(GAME_TYPE_EXTRA, -1);
        boolean isNewGame = intent.getBooleanExtra(NEW_GAME_EXTRA, true);
        mPhrase = intent.getStringExtra(PHRASE_EXTRA);

        setUpGame(gameType, isNewGame);
        score.set_upper_limit(wordList.size());

        // Set the first value to appear on screen
        wordList.get(0).updateUserWord("", false);
        updateParagraphText();

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
                    timer.cancel();
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

        // Timer stuff
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ++seconds;
                        if (seconds >= 60) {
                            ++minutes;
                            seconds = 0;
                        }

                        StringBuilder sb = new StringBuilder();
                        sb.append("Timer: " + minutes + ":");
                        if (seconds < 10)
                            sb.append("0");
                        sb.append(seconds);

                        timerView.setText(sb.toString());
                    }
                });
            }
        }, 1000, 1000);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home)
            finish();
        return false;
    }

    private void setUpTextSize(String textSize) {
        if (textSize == null)
            throw new IllegalArgumentException("textSize is null");

        if (textSize.equals("Small")) {
            paragraphView.setTextSize(12.0f);
        }
        else if (textSize.equals("Medium")) {
            paragraphView.setTextSize(20.0f);
        }
        else if (textSize.equals("Large")) {
            paragraphView.setTextSize(26.0f);
        }
        else {
            throw new IllegalArgumentException("Bad text size " + textSize);
        }
    }

    /**
     * Ends the game and starts the GameOver activity
     *
     * @param
     */
    public void gameOver() {
        for (int i = 0; i < wordList.size(); i++)
            if (wordList.get(i).isCorrect())
                score.add_word_score(wordList.get(i).getCorrectWord().length());

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", score.get_score());
        editor.commit();

        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("score", score.get_score());
        intent.putExtra("mistakes", score.get_mistakes());
        intent.putExtra("game type", gameType);
        intent.putExtra("seconds", seconds);
        intent.putExtra("minutes", minutes);
        intent.putExtra(PHRASE_EXTRA, mPhrase);
        startActivity(intent);
        finish();
    }

    /**
     * Checks to make sure that we have a good game type and then begins to build the game state
     * If the game is new, then we get a random word from thw corresponding word file
     * If the game is not new, then there must be a valid phrase that is going to be used in the game
     *
     * @param gameType The game type we want to play. 0 for single word, 1 for multiword, 2 for paragraph
     * @param isNewGame Is the game new or a retry
     */
    public void setUpGame(int gameType, boolean isNewGame) {
        Log.d(TAG, mPhrase);
        if(gameType < 0 || gameType > 2)
            throw new IllegalArgumentException("Game Type is not valid: " + gameType);
        if (!isNewGame && (mPhrase == null || mPhrase.length() == 0))
            throw new IllegalArgumentException("Phrase is null or zero length on a retry game");

        if (isNewGame) {
            switch (gameType) {
                case 0:
                    mPhrase = getStringFromFile(R.raw.single_word);
                    break;
                case 1:
                    mPhrase = getStringFromFile(R.raw.multiple_words);
                    break;
                case 2:
                    mPhrase = getStringFromFile(R.raw.paragraph);
                    break;
                default:
                    throw new IllegalStateException("Should not be in default section");
            }
        }

        buildCorrectWordList(mPhrase);
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

        for (int i = 1; i < numWordsTotal; i++) {
            String prevUserWord = list.get(i-1).getUserWord();
            String prevCurrWord = list.get(i-1).getCorrectWord();

            // Checks if we need to add a space or not before the current word
            if (prevUserWord == null || prevUserWord.length() < prevCurrWord.length() || list.get(i).isTyped())
                toReturn.append(" ");

            if (list.get(i).isTyped())
                toReturn.append(list.get(i).getColoredIWord());
            else
                toReturn.append(list.get(i).getColoredCWord());
        }

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
    private String getStringFromFile(int file) {
        InputStream input = getResources().openRawResource(file);
        Scanner scan = new Scanner(input);

        // Make arraylist for all words in file
        ArrayList<String> fileWordList = new ArrayList<>(0);

        // Put all words in fileWordList
        while (scan.hasNextLine()) {
            String item = scan.nextLine();
            fileWordList.add(item);
        }

        // Get random index
        Random random = new Random();
        int randomIndex = random.nextInt(fileWordList.size());

        // Debug
        Log.d(TAG, "Index: " + randomIndex);
        Log.d(TAG, "File Word Size: " + fileWordList.size());

        // Close file and make new scanner for random chosen text
        scan.close();

        return fileWordList.get(randomIndex);

    }

    private void buildCorrectWordList(String phrase) {
        Scanner scan = new Scanner(phrase);

        // Go through text and make WordNode's
        while (scan.hasNext()) {
            String word = scan.next();
            wordList.add(new WordNode(word));
        }

        // Setup
        numWordsTotal = wordList.size();
        numWordsTyped = 0;

        scan.close();
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
