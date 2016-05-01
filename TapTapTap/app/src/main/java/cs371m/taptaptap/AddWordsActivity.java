package cs371m.taptaptap;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AddWordsActivity extends AppCompatActivity implements OnItemSelectedListener {

    private final String TAG = "AddWords";

    // Max Chars stuff
    private String maxCharsString;
    private final int[] maxCharNum = {15, 45, 90};

    private int maxCharNumPos = 0;

    private EditText editText;

    private InputFilter characterFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);

        maxCharsString = getResources().getString(R.string.max_chars);

        // InputFilter
        characterFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    char c = source.charAt(i);
                    if (!Character.isLetterOrDigit(c) && !(Character.isSpaceChar(c) && maxCharNumPos > 0)) {
                        Toast.makeText(getApplicationContext(), "Illegal Character.",Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        };

        // Views and buttons
        final Spinner spinner = (Spinner) findViewById(R.id.gametype_spinner);
        spinner.setAdapter(new MyAdapter(this, R.layout.spinner_item, getResources().getStringArray(R.array.list_game_type)));
        spinner.setOnItemSelectedListener(this);

        editText = (EditText) findViewById(R.id.add_words_edit_text);

        Button button = (Button) findViewById(R.id.add_words_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemId = (int) spinner.getSelectedItemId();
                String text = "Item Id = " + itemId;
                Log.d(TAG, text);

                String input = editText.getText().toString();
                boolean cleanExit = writeStringToDatabase(input, itemId);
                StringBuilder sb = new StringBuilder();

                if (!cleanExit) {
                    sb.append("Could not save input correctly.");
                }
                else {
                    sb.append("Saved input!");
                }

                Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Reset the Length
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCharNum[position]), characterFilter});

        // If the current text length is too long for the current game type
        // Reset the edit text and tell the user why
        Log.d(TAG, "Edit Text Length " + editText.getText().length());
        Log.d(TAG, "MaxChaNum = " + maxCharNum[position]);
        if (editText.getText().length() > maxCharNum[position]) {
            editText.setText("");
            Toast.makeText(parent.getContext(), "Too much text for this game type.",Toast.LENGTH_SHORT).show();
        }

        maxCharNumPos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private boolean writeStringToDatabase(String input, int gametype) {
        Database database = new Database(this);
        database.insertPhrase(input, gametype);
        return true;
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
