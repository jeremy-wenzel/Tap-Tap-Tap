package cs371m.taptaptap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Database class for interacting with the SQLite Database. If the databased is opened,
 * the database must be closed as soon as it is done being used. If not, it is possible
 * that multiple acitivies have connections to the database, which would cause errors.
 */
public class Database {

    private static final String TAG = "Database";

    private SQLiteDatabase db;
    private DatabaseOpenHelper openHelper;

    // Score Table
    public static final String SCORE_TABLE = "scores";
    public static final String SCORE_COL = "score";
    public static final String CORRECT_WPM_COL = "cwpm";
    public static final String GAME_TYPE_COL = "gametype";

    // Words Table
    public static final String WORDS_TABLE = "words";
    public static final String PHRASE_COL = "phrase";

    // Context
    Context context;    // Needed to access the database

    /**
     * Database Constructor. Sets up the database and creates one if one does not exist.
     *
     * @param context
     */
    public Database(Context context) {
        openHelper = new DatabaseOpenHelper(context.getApplicationContext());
        this.context = context.getApplicationContext();
    }

    /**
     * Deletes all scores and phrases from database and reenters Raw files back into database
     */
    public void resetDatabase() {
        Log.d(TAG, "beginning resetDatabase");
        // Delete all entries from database
        resetAllPhrases();
        resetHighScores();

        // Insert all gametype files into database
        initializeDatabase();
        Log.d(TAG, "Finishing resetDatabase");
    }

    public void resetAllPhrases() {
        openDatabaseConnection();
        openHelper.deleteAllPhrases(db);
        initializeDatabase();
        closeDatabaseConnection();
    }

    public void resetHighScores() {
        openDatabaseConnection();
        openHelper.deleteAllHighScores(db);
        closeDatabaseConnection();

    }

    /**
     * Inserts all words from Raw files into the database
     */
    private void initializeDatabase() {
        for (int i = 0; i < 3; ++i)
            buildGameTypePhrases(i);
    }

    /**
     * Builds the database for the game type
     * @param gameType
     */
    private void buildGameTypePhrases(int gameType) {
        int fileId = -1;

        // Get the file for the gametype
        switch (gameType) {
            case 0 :
                fileId = R.raw.single_word;
                break;
            case 1 :
                fileId = R.raw.multiple_words;
                break;
            case 2 :
                fileId = R.raw.paragraph;
                break;
        }

        InputStream input = context.getResources().openRawResource(fileId);
        Scanner scan = new Scanner(input);

        // Put all words into the database
        while (scan.hasNextLine()) {
            String phrase = scan.nextLine();
            insertPhrase(phrase, gameType);
        }
    }

    /**
     * Opens a database connection. The database must be closed as soon as it is done being used
     * See @closeDatabaseConnection()
     */
    private void openDatabaseConnection() {
        db = openHelper.getWritableDatabase();
    }

    /**
     * Closes the database connection.
     */
    private void closeDatabaseConnection() {
        openHelper.close();
    }

    /**
     * Proof of concept method to ge the scores. May not be the final version. Was just trying
     * to see if something worked.
     * @return
     */
    public ArrayList<ScoreSystem> getGameTypeScores(int gameType) {
        Log.d(TAG, "In getGameTypeScores()");

        ArrayList<ScoreSystem> toReturn = new ArrayList<>(0);
        openDatabaseConnection();

        Cursor c = db.query(SCORE_TABLE, null, GAME_TYPE_COL + "=" + gameType, null, null, null, SCORE_COL + " DESC");

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int scoreIndex = c.getColumnIndex(SCORE_COL);
            int cwpmIndex = c.getColumnIndex(CORRECT_WPM_COL);

            int score = c.getInt(scoreIndex);
            double cwpm = c.getFloat(cwpmIndex);

            Log.d(TAG, "Score = " + score);

            toReturn.add(new ScoreSystem(score, cwpm, gameType));
            c.move(1);
        }

        closeDatabaseConnection();
        return toReturn;
    }

    public ArrayList<ScoreSystem> getAllGameTypeScores() {
        Log.d(TAG, "In getGameTypeScores()");

        ArrayList<ScoreSystem> toReturn = new ArrayList<>(0);
        openDatabaseConnection();

        Cursor c = db.query(SCORE_TABLE, null, null, null, null, null, SCORE_COL + " DESC");

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int scoreIndex = c.getColumnIndex(SCORE_COL);
            int cwpmIndex = c.getColumnIndex(CORRECT_WPM_COL);
            int gameTypeIndex = c.getColumnIndex(GAME_TYPE_COL);

            int score = c.getInt(scoreIndex);
            double cwpm = c.getDouble(cwpmIndex);
            int gameType = c.getInt(gameTypeIndex);

            Log.d(TAG, "Score = " + score);

            toReturn.add(new ScoreSystem(score, cwpm, gameType));
            c.move(1);
        }

        closeDatabaseConnection();
        return toReturn;
    }

    public void insertScore(int score, double correctWordsPerMinute, int gameType) {
        Log.d(TAG, "Inserting scores");

        openDatabaseConnection();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SCORE_COL, score);
        contentValues.put(CORRECT_WPM_COL, correctWordsPerMinute);
        contentValues.put(GAME_TYPE_COL, gameType);

        db.insert(SCORE_TABLE, null, contentValues);
        closeDatabaseConnection();
    }

    public ArrayList<String> getAllPhrasesByGameType(int gameType) {
        Log.d(TAG, "getPhrase");

        openDatabaseConnection();

        ArrayList<String> toReturn = new ArrayList<>();
        Cursor c = db.query(WORDS_TABLE, null, GAME_TYPE_COL + "=" + gameType, null, null, null, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int phraseIndex = c.getColumnIndex(PHRASE_COL);
            String phrase = c.getString(phraseIndex);
            Log.d(TAG, "Phrase = " + phrase);
            toReturn.add(phrase);
            c.move(1);
        }

        closeDatabaseConnection();
        return toReturn;
    }

    public String getRandomPhraseByGameType(int gameType) {
        Log.d(TAG, "getPhrase");

        openDatabaseConnection();

        Random rand = new Random();
        Cursor c = db.query(WORDS_TABLE, null, GAME_TYPE_COL + "=" + gameType, null, null, null, null);
        int offset = rand.nextInt(c.getCount());

        c.moveToFirst();
        c.move(offset);
        int phraseIndex = c.getColumnIndex(PHRASE_COL);
        String phrase = c.getString(phraseIndex);
        Log.d(TAG, "Phrase = " + phrase);

        closeDatabaseConnection();
        return phrase;
    }

    public void insertPhrase(String phrase, int gametype) {
        Log.d(TAG, "Inserting Phrase");

        openDatabaseConnection();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PHRASE_COL, phrase);
        contentValues.put(GAME_TYPE_COL, gametype);

        db.insert(WORDS_TABLE, null, contentValues);
        closeDatabaseConnection();
    }

    public void deletePhrase(String phrase) {
        Log.d(TAG, "Delete Phrase");

        openDatabaseConnection();
        openHelper.deletePhrase(phrase);
        closeDatabaseConnection();
    }

    public boolean isWordsInGameType(int gameType) {
        Log.d(TAG, "isWordsInGameType");

        openDatabaseConnection();
        Cursor c = db.query(WORDS_TABLE, null, GAME_TYPE_COL + "=" + gameType, null, null, null, null);

        boolean toReturn = c.getCount() > 0;
        closeDatabaseConnection();

        return  toReturn;
    }

    /**
     * Helper class that makes the connection to the actual database and opens it. This also
     * creates the database if one does not already exist
     */
    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "TapTapTap.db";

        private static final String SQL_CREATE_SCORE =
                "CREATE TABLE " + SCORE_TABLE + " (" +
                        SCORE_COL + " INT," +
                        CORRECT_WPM_COL + " REAL," +
                        GAME_TYPE_COL + " INT" + ")";
        private static final String SQL_CREATE_WORDS =
                "CREATE TABLE " + WORDS_TABLE + " (" +
                        PHRASE_COL + " TEXT," +
                        GAME_TYPE_COL + " INT" + ")";

        private static final String SQL_DELETE_ALL_WORDS =
                "DELETE FROM " + WORDS_TABLE;
        private static final String SQL_DELETE_ALL_HIGH_SCORES =
                "DELETE FROM " + SCORE_TABLE;

        private final String TAG = "DatabaseOpenHelper";

        public DatabaseOpenHelper (Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("DatabaseHelper", "Database being created");
            // Create the database
            db.execSQL(SQL_CREATE_SCORE);
            db.execSQL(SQL_CREATE_WORDS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE);
            onCreate(db);
        }

        public void deleteAllPhrases(SQLiteDatabase db) {
            db.execSQL(SQL_DELETE_ALL_WORDS);
        }

        public void deleteAllHighScores(SQLiteDatabase db) {
            db.execSQL(SQL_DELETE_ALL_HIGH_SCORES);
        }

        public void deletePhrase(String phrase) {
            db.execSQL("DELETE FROM " + WORDS_TABLE + " WHERE " + PHRASE_COL + " = '" + phrase +"'");
        }
    }
}
