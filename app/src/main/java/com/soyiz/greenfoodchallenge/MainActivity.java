package com.soyiz.greenfoodchallenge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity {

    public static final boolean DEBUG_MODE = true;

    public static final int RC_LOGIN_ACTIVITY = 123;

    public static final String TAG = "MainActivity";
    public static final String FRAGMENT_EATING_HABITS = "fragment_EatingHabits";
    public static final String FRAGMENT_ECO = "fragment_Eco";
    public static final String FRAGMENT_PLEDGE = "fragment_Pledge";

    private Fragment eatingHabitsFragment = null;
    private Fragment ecoFragment = null;
    private Fragment pledgeFragment = null;
    private Fragment userFragment = null;
    public static final String FRAGMENT_USER = "fragment_User";
    public static final String CURRENT_FRAGMENT = "current_Fragment";
    private static String previousFragment = null;
    private FragmentManager fragmentManager = null;
    private BottomNavigationView bottomNavigationView = null;
    private String currentFragment = null;

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
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("disableShiftMode", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("disableShiftMode", "Unable to change value of shift mode", e);
        }
    }

    public void startLogin() {
        startActivityForResult(new Intent(this, LoginActivity.class), RC_LOGIN_ACTIVITY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: saving current fragment as " + currentFragment);
        previousFragment = currentFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

//        Debug code for getting facebook login hash
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.soyiz.greenfoodchallenge",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d(TAG, "onCreate: name not found exception");
//        } catch (NoSuchAlgorithmException e) {
//            Log.d(TAG, "onCreate: no such algorithm exception");
//        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (DEBUG_MODE == false) {
            if (currentUser == null) {
                Log.d(TAG, "onCreate: user not previously logged in, going to AuthUI");
                startLogin();

                return;
            }
        }

        if (User.getCurrent().getFirebaseUser() == null) {
            User.getCurrent().setFirebaseUser(currentUser);
        }

        if (previousFragment != null) {
            currentFragment = previousFragment;
            Log.d(TAG, "onCreate: currentFragment set as " + previousFragment);
        } else {
            currentFragment = FRAGMENT_PLEDGE;
            Log.d(TAG, "onCreate: currentFragment defaulting to " + FRAGMENT_PLEDGE);
        }

        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_nav);

        disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_item_EatingHabits:
                        changeFragment(FRAGMENT_EATING_HABITS);
                        return true;
                    case R.id.bottom_nav_item_Eco:
                        changeFragment(FRAGMENT_ECO);
                        return true;
                    case R.id.bottom_nav_item_Pledge:
                        changeFragment(FRAGMENT_PLEDGE);
                        return true;
                    case R.id.bottom_nav_item_User:
                        changeFragment(FRAGMENT_USER);
                        return true;
                }
                return false;
            }
        });

        changeFragment(currentFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_LOGIN_ACTIVITY) {
            Log.d(TAG, "onActivityResult: successful login");
        } else {
            Log.d(TAG, "onActivityResult: incorrect requestCode for login activity trigger. Was: " + requestCode + ", expected: " + RC_LOGIN_ACTIVITY);
        }
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

    private void changeFragment(String fragment) {
        if (eatingHabitsFragment == null) {
            eatingHabitsFragment = new EatingHabitsFragment();
        }

        if (ecoFragment == null) {
            ecoFragment = new EcoFragment();
        }

        if (pledgeFragment == null) {
            pledgeFragment = new PledgeFragment();
        }

        if (userFragment == null) {
            userFragment = new UserFragment();
        }

        Fragment newFragment = null;
        int newFragmentNavID = 0;
        int newFragmentNameID = 0;

        switch (fragment) {
            case FRAGMENT_EATING_HABITS:
                newFragment = eatingHabitsFragment;
                newFragmentNavID = R.id.bottom_nav_item_EatingHabits;
                newFragmentNameID = R.string.eating_habits_name;
                break;

            case FRAGMENT_ECO:
                newFragment = ecoFragment;
                newFragmentNavID = R.id.bottom_nav_item_Eco;
                newFragmentNameID = R.string.eco_name;
                break;

            case FRAGMENT_PLEDGE:
                newFragment = pledgeFragment;
                newFragmentNavID = R.id.bottom_nav_item_Pledge;
                newFragmentNameID = R.string.pledge_name;
                break;

            case FRAGMENT_USER:
                newFragment = userFragment;
                newFragmentNavID = R.id.bottom_nav_item_User;
                newFragmentNameID = R.string.user_name;
                break;
        }

        Log.d(TAG, "changing to " + fragment + " fragment");
        currentFragment = fragment;
        fragmentReplaceTransaction(newFragment);
        bottomNavigationView.getMenu().findItem(newFragmentNavID).setChecked(true);
        setActionBarTitle(newFragmentNameID);
    }
}
