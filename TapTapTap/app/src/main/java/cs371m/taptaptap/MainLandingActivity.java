package cs371m.taptaptap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainLandingActivity extends AppCompatActivity {

    public CheckBox dontShowAgain;

    private final String GAME_TYPE_EXTRA = "GameType";
    private final String NEW_GAME_EXTRA = "NewGame";
    private final String PHRASE_EXTRA = "Phrase";

    private SoundPool mSounds;
    private int mButtonSoundID;
//    private int mCheckSoundID;
//    private int mKeyboardSoundID;


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
        TextView textView = (TextView) eulaLayout.findViewById(R.id.greeting_textview);
        textView.setText(R.string.how_to_navigate);
        dialogBox.setView(eulaLayout);
        dialogBox.setTitle("Welcome to TapTapTap!!!");

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

                return;
            }
        });

        if(!skipMessage.equals("checked")){
            dialogBox.show();
        }

        mSounds = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        mButtonSoundID = mSounds.load(this, R.raw.button_click, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_action:
                //Log.d(GAME_TYPE_EXTRA, findViewById(R.id.sound).isEnabled() + "");
//                Log.d(EXTRA, getSharedPreferences("prefs", 0).getBoolean("sound", true) + "");
                //if(getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("sound", true)){ mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1); }
                mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1);
                startActivity(new Intent(this, SettingsActivity.class));

                return true;
            case R.id.about_action:
                mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1);
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }

        return false;
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

//<<<<<<< HEAD
//    public void startSingleWordGame(View view) {
//
//        if(getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("sound", true)){ mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1); }
//=======
    private void startTapActivity(int gameType) {
//>>>>>>> ecb38c723bfc96471282f43f99a6c53d9829c17d
        mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1);
        Intent intent = new Intent(this, TapActivity.class);
        intent.putExtra(GAME_TYPE_EXTRA, gameType);
        intent.putExtra(NEW_GAME_EXTRA, true);
        intent.putExtra(PHRASE_EXTRA, "");
        startActivity(intent);
    }

    public void startSingleWordGame(View view) {
        startTapActivity(0);
    }

//    public void startMultiwordGame(View view) {
//<<<<<<< HEAD
//        if(getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("sound", true)){ mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1); }
//        Intent intent = new Intent(this, TapActivity.class);
//        intent.putExtra(NEW_GAME_EXTRA, 1);
//        startActivity(intent);
//    }

//    public void startParagraphGame(View view) {
//        if(getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("sound", true)){ mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1); }
//        Intent intent = new Intent(this, TapActivity.class);
//        intent.putExtra(EXTRA, 2);
//=======
//        startTapActivity(1);
//    }

    public void startMultiwordGame(View view){
        startTapActivity(1);
    }

    public void startParagraphGame(View view) {
        startTapActivity(2);
    }


    public void highScores(View view) {
        mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1);
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }

    public void addWords(View view) {
        mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1);
        Intent intent = new Intent(this, AddWordsGameTypeActivity.class);
        startActivity(intent);
    }

    public void howToPlay(View view) {
        mSounds.play(mButtonSoundID, 1, 1, 1, 0, 1);
        Intent intent = new Intent(this, HowToPlayActivity.class);

        startActivity(intent);
    }
}
