package cs371m.taptaptap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreActivity
        extends AppCompatActivity
        implements ActionBar.TabListener {

    private static final String TAG = "HighScoreActivity";
    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    public static final String[] sectionTabs = {"Single Word", "Multiple Words", "Paragraph", "Overall"};

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    private ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());



        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home)
            finish();
        return false;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new GameTypeFragment();
            Bundle args = new Bundle();
            args.putInt(GameTypeFragment.ARG_SECTION_NUMBER, i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return sectionTabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return sectionTabs[position];
        }
    }


    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class GameTypeFragment extends ListFragment    {

        public static final String ARG_SECTION_NUMBER = "section_number";
        private int gameType;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle args = getArguments();
            gameType = args.getInt(ARG_SECTION_NUMBER);

            View rootView = inflater.inflate(R.layout.high_score_fragment, container, false);
            return rootView;

        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Database database = new Database(getActivity());
            ArrayList<ScoreSystem> gameScores;
            Log.d(TAG, "Gametype = " + gameType);
            switch (gameType) {
                case 0:
                    gameScores = database.getGameTypeScores(gameType);
                    break;
                case 1:
                    gameScores = database.getGameTypeScores(gameType);
                    break;
                case 2:
                    gameScores = database.getGameTypeScores(gameType);
                    break;
                default:
                    gameScores = database.getAllGameTypeScores();
            }

            setListAdapter(new HighScoreAdapter(getActivity(), R.layout.score_list_view, gameScores));
        }

        /**
         * Lol, a class inside a class inside a class
         */
        public class HighScoreAdapter extends ArrayAdapter<ScoreSystem> {

            ArrayList<ScoreSystem> objects;
            Context context;

            public HighScoreAdapter(Context ctx, int textViewResourceId, ArrayList<ScoreSystem> objects) {
                super(ctx, textViewResourceId, objects);
                this.objects = objects;
                context = ctx;
            }

            @Override
            public View getView(int position, View contextView, ViewGroup parent) {
                return getCustomView(position, contextView, parent);
            }

            public View getCustomView(int position, View convertView, ViewGroup parent) {
                // Inflate the View
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View scoreListView = inflater.inflate(R.layout.score_list_view, parent, false);

                TextView mainScore = (TextView) scoreListView.findViewById(R.id.total_score_text_view);
                mainScore.setText("Total Score: " + objects.get(position).get_score());

                TextView correctWPM = (TextView) scoreListView.findViewById(R.id.correct_wpm);
                correctWPM.setText("Correct Words Per Minute: " + objects.get(position).getCorrectWordsPerMinute());

                TextView gameTypeView = (TextView) scoreListView.findViewById(R.id.game_type_text_view);
                gameTypeView.setText("Game Type: " + objects.get(position).getGameType());

                return scoreListView;
            }
        }
    }


}