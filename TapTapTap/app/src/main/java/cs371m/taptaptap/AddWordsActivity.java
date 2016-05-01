package cs371m.taptaptap;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AddWordsActivity extends AppCompatActivity {

    private final String TAG = "AddWords";

    // Max Chars stuff
    private String maxCharsString;
    private final int[] maxCharNum = {15, 45, 90};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);

        maxCharsString = getResources().getString(R.string.max_chars);

        final Spinner spinner = (Spinner) findViewById(R.id.gametype_spinner);
        spinner.setAdapter(new MyAdapter(this, R.layout.spinner_item, getResources().getStringArray(R.array.list_game_type)));

        Button button = (Button) findViewById(R.id.add_words_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();
                Log.d(TAG, text);
                finish();
            }
        });
    }

    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context ctx, int textViewResourceId, String[] objects) {
            super(ctx, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int position, View contextView, ViewGroup parent) {
            return getCustomView(position, contextView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // Inflate the View
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.spinner_item, parent, false);

            // Set the first main text
            TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_main);
            main_text.setText(getResources().getStringArray(R.array.list_game_type)[position]);

            // Set the second sub text
            TextView sub_text = (TextView) mySpinner.findViewById(R.id.spinner_sub);
            sub_text.setText(maxCharsString + maxCharNum[position]);

            return mySpinner;
        }
    }

}
