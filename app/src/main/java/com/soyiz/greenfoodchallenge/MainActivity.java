package com.soyiz.greenfoodchallenge;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
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

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;

    private Fragment eatingHabitsFragment = null;
    private Fragment ecoFragment = null;
    private Fragment pledgeFragment = null;
    private Fragment userFragment = null;

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
                        case R.id.bottom_nav_item_Pledge:
                            changeToPledgeFragment();
                            return true;
                        case R.id.bottom_nav_item_User:
                            changeToUserFragment();
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
        disableShiftMode(bottomNavigationView);

        fragmentManager = getSupportFragmentManager();
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        bottomNavigationView.getMenu().findItem(R.id.bottom_nav_item_Pledge).setChecked(true);
        changeToPledgeFragment();
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

    private void changeToPledgeFragment() {
        if (pledgeFragment == null) {
            pledgeFragment = new PledgeFragment();
        }

        Log.d(TAG, "changing to Pledge fragment");
        fragmentReplaceTransaction(pledgeFragment);
        setActionBarTitle(R.string.pledge_name);
    }

    private void changeToUserFragment() {
        if (userFragment == null) {
            userFragment = new UserFragment();
        }

        Log.d(TAG, "changing to User fragment");
        fragmentReplaceTransaction(userFragment);
        setActionBarTitle(R.string.user_name);
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);

        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");

            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {

                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}
