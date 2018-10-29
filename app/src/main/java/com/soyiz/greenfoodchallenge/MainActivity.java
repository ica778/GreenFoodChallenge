package com.soyiz.greenfoodchallenge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private Fragment eatingHabitsFragment = null;
    private Fragment ecoFragment = null;
    private Fragment infoFragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bottom_nav_item_EatingHabits:
                            changeToEatingHabitsFragment();
                            return true;
                        case R.id.bottom_nav_item_Eco:
                            changeToEcoFragment();
                            return true;
                        case R.id.bottom_nav_item_Info:
                            changeToInfoFragment();
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(
                R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationView.getMenu().findItem(R.id.bottom_nav_item_Eco).setChecked(true);
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_fragment_holder);
        changeToEcoFragment();
    }

    private void fragmentReplaceTransaction(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.frame_fragment_holder, fragment)
                .commit();
    }

    private void setActionBarTitle(int id) {
        ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_textView))
                .setText(id);
    }

    // Can't make these one function, due to having to call a specific constructor for each one
    private void changeToEatingHabitsFragment() {
        if (eatingHabitsFragment == null) {
            eatingHabitsFragment = new EatingHabitsFragment();
        }

        Log.d(TAG, "changing to EatingHabits fragment");
        fragmentReplaceTransaction(eatingHabitsFragment);
        setActionBarTitle(R.string.eating_habits_name);
    }

    private void changeToEcoFragment() {
        if (ecoFragment == null) {
            ecoFragment = new EcoFragment();
        }

        Log.d(TAG, "changing to Eco fragment");
        fragmentReplaceTransaction(ecoFragment);
        setActionBarTitle(R.string.eco_name);
    }

    private void changeToInfoFragment() {
        if (infoFragment == null) {
            infoFragment = new InfoFragment();
        }

        Log.d(TAG, "changing to Info fragment");
        fragmentReplaceTransaction(infoFragment);
        setActionBarTitle(R.string.info_name);
    }
}
