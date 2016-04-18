package cs371m.taptaptap;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rafik on 4/4/2016.
 */
//public class HighScoreActivity extends AppCompatActivity {
//
//    int highScores[] = new int[10];
//    int score;
//    TextView statsView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.high_score);
//
//        Database helper = new Database(this);
//        helper.insertScore(1, 1);
//        ArrayList<Integer> list = helper.getAllGameTypeScores();
//
//        statsView = (TextView) findViewById(R.id.high_score_field);
//
//        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
//        score = prefs.getInt("score", 0);
//        statsView.setText("#1 - " + score);
//    }
//}

public class HighScoreActivity extends ListActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database database = new Database(this);
        ArrayList<Integer> scores = database.getAllGameTypeScores();
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.score_list_view, scores);
        setListAdapter(adapter);
    }
}