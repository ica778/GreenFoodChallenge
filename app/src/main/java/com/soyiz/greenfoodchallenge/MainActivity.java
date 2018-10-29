package com.soyiz.greenfoodchallenge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.concurrent.Callable;

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
                            Log.d(TAG, "changing to EatingHabits fragment");
                            return true;
                        case R.id.bottom_nav_item_Eco:
                            changeToEcoFragment();
                            Log.d(TAG, "changing to Eco fragment");
                            return true;
                        case R.id.bottom_nav_item_Info:
                            changeToInfoFragment();
                            Log.d(TAG, "changing to Info fragment");
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(
                R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationView.getMenu().findItem(R.id.bottom_nav_item_Eco).setChecked(true);
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_fragment_holder);
        if (fragment == null) {
            if (ecoFragment == null) {
                ecoFragment = new EcoFragment();
            }
            fragmentManager.beginTransaction()
                    .add(R.id.frame_fragment_holder, ecoFragment)
                    .commit();
        }
    }

    private void fragmentReplaceTransaction(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.frame_fragment_holder, fragment)
                .commit();
    }

    // Can't make these one function, due to having to call a specific constructor for each one
    private void changeToEatingHabitsFragment() {
        if (eatingHabitsFragment == null) {
            eatingHabitsFragment = new EatingHabitsFragment();
        }
        fragmentReplaceTransaction(eatingHabitsFragment);
    }

    private void changeToEcoFragment() {
        if (ecoFragment == null) {
            ecoFragment = new EcoFragment();
        }
        fragmentReplaceTransaction(ecoFragment);
    }

    private void changeToInfoFragment() {
        if (infoFragment == null) {
            infoFragment = new InfoFragment();
        }
        fragmentReplaceTransaction(infoFragment);
    }
}
