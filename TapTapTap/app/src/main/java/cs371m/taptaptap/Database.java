package cs371m.taptaptap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Database class for interacting with the SQLite Database. If the databased is opened,
 * the database must be closed as soon as it is done being used. If not, it is possible
 * that multiple acitivies have connections to the database, which would cause errors.
 */
public class Database {

    private static final String TAG = "Database";

    private SQLiteDatabase db;
    private DatabaseOpenHelper openHelper;

    public static final String SCORE_TABLE = "scores";
    public static final String SCORE_ID = "scoreid";    // Don't know if we are gonna use this yet
    public static final String SCORE_COL = "score";
    public static final String GAME_TYPE_COL = "gametype";

    /**
     * Database Constructor. Sets up the database and creates one if one does not exist.
     *
     * @param context
     */
    public Database(Context context) {
        openHelper = new DatabaseOpenHelper(context.getApplicationContext());
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
    public ArrayList<Integer> getScores() {
        Log.d(TAG, "In getScores()");
        openDatabaseConnection();

        // This code is just proof of concept. It actually doesn't do anything
        // However, some of this code could be adapted to fit our needs

        Cursor c = db.query(SCORE_TABLE, new String[]{SCORE_COL, GAME_TYPE_COL},
                null, null, null, null, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            int scoreindex = c.getColumnIndex(SCORE_COL);
            int score = c.getInt(scoreindex);
            Log.d(TAG, "Score = " + score);
            c.move(1);
        }

        closeDatabaseConnection();
        return null;
    }

    /**
     * Proof of concept method to ge the scores. May not be the final version. Was just trying
     * to see if something worked.
     */
    public void insertScore(int score, int gametype) {
        Log.d(TAG, "Inserting scores");
        openDatabaseConnection();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(SCORE_ID, 1);

        // This code is just proof of concept. It actually doesn't do anything
        // However, some of this code could be adapted to fit our needs

        contentValues.put(SCORE_COL, 20);
        contentValues.put(GAME_TYPE_COL, gametype);

        db.insert(SCORE_TABLE, null, contentValues);
        closeDatabaseConnection();
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
                        GAME_TYPE_COL + " INT" + ")";

        private final String TAG = "DatabaseOpenHelper";

        public DatabaseOpenHelper (Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("DatabaseHelper", "Database being created");
            // Create the database
            db.execSQL(SQL_CREATE_SCORE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /* Don't need to do anything. Need method to be overridden though */
        }
    }

}
