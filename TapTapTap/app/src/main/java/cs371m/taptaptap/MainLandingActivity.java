package cs371m.taptaptap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

public class MainLandingActivity extends AppCompatActivity {

    public CheckBox dontShowAgain;

    private final String EXTRA = "GameType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing);


    }

    @Override
    protected void onResume() {
        super.onResume();
        AlertDialog.Builder dialogBox = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.greating_layout, null);
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        String skipMessage = settings.getString("skipMessage", "NOT checked");

        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        dialogBox.setView(eulaLayout);
        dialogBox.setTitle("Hello");
        dialogBox.setMessage("This is TapTapTap. Its an App that helps you text faster." +
                "\nRules: " +
                "\n1) Every correct letters  that you type gives you a point." +
                "\n2) every mistake you make takes away a point." +
                "\n3) Fully Complete words give you bonus points." +
                "\nThe game ends once you have completed the provided text. " +
                "\nHave fun :)");


        dialogBox.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBox = "NOT checked";

                if (dontShowAgain.isChecked()) {
                    checkBox = "checked";
                }

                SharedPreferences settings = getSharedPreferences("prefs", 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString("skipMessage", checkBox);
                editor.commit();

                // Do what you want to do on "OK" action

                return;
            }
        });

        if(!skipMessage.equals("checked")){
            dialogBox.show();
        }

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

    public void startSingleWordGame(View view) {
        Intent intent = new Intent(this, TapActivity.class);
        intent.putExtra(EXTRA, 0);
        startActivity(intent);
    }

    public void startMultiwordGame(View view) {
        Intent intent = new Intent(this, TapActivity.class);
        intent.putExtra(EXTRA, 1);
        startActivity(intent);
    }

    public void startParagraphGame(View view) {
        Intent intent = new Intent(this, TapActivity.class);
        intent.putExtra(EXTRA, 2);
        startActivity(intent);
    }

    public void highScores(View view) {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
