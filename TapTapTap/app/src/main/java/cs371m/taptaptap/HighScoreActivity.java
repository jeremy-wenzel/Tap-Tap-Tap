
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