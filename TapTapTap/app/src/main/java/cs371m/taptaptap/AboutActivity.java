package cs371m.taptaptap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        TextView textView = (TextView) findViewById(R.id.about_section);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(getString(R.string.about_desc));
        textView.setTextSize(19.0f);
        textView.setTextColor(Color.BLACK);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home)
            finish();
        return false;
    }
}
