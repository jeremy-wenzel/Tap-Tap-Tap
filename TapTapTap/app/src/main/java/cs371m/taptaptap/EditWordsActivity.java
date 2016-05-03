package cs371m.taptaptap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class EditWordsActivity extends AppCompatActivity implements ActionBar.TabListener {

    private static final String TAG = "EditWordsActivity";

    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    public static final String[] sectionTabs = {"Single Word", "Multiple Words", "Paragraph"};

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_dictionary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home)
            finish();
        else if (menuItem.getItemId() == R.id.add_word) {
            Intent intent = new Intent(this, AddWordsActivity.class);
            startActivity(intent);
        }
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
            Fragment fragment = new GameTypeDictionaryFragment();
            Bundle args = new Bundle();
            args.putInt(GameTypeDictionaryFragment.ARG_SECTION_NUMBER, i);
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
    public static class GameTypeDictionaryFragment extends ListFragment {

        public static final String ARG_SECTION_NUMBER = "section_number";
        private int gameType;
        private Database database;
        private ArrayAdapter adapter;

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
            database = new Database(getActivity());
            ArrayList<String> gameDictionary = new ArrayList<>();
            Log.d(TAG, "Gametype = " + gameType);
            switch (gameType) {
                case 0:
                    gameDictionary = database.getAllPhrasesByGameType(gameType);
                    break;
                case 1:
                    gameDictionary = database.getAllPhrasesByGameType(gameType);
                    break;
                case 2:
                    gameDictionary = database.getAllPhrasesByGameType(gameType);
                    break;
            }

            adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, gameDictionary);
            setListAdapter(adapter);

            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                    final AdapterView<?> view = arg0;
                    final int position = arg2;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete \n\""
                                        + view.getItemAtPosition(position).toString() + "\" ?")
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                            Toast.makeText(getActivity(),
                                                    view.getItemAtPosition(position).toString(),
                                                    Toast.LENGTH_LONG).show();

                                            database.deletePhrase(view.getItemAtPosition(position).toString());
                                            resetListView();
                                        }
                                    })
                            .show();

                    return true;
                }
            });
        }

        @Override
        public void onResume(){
            super.onResume();
            resetListView();
        }

        private void resetListView() {
            adapter.clear();
            adapter.addAll(database.getAllPhrasesByGameType(gameType));
            adapter.notifyDataSetChanged();
        }
    }
}
