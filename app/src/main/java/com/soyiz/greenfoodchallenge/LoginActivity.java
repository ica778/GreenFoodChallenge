package com.soyiz.greenfoodchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: starting login");
        createSignInIntent();
    }

    public void createSignInIntent() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        Log.d(TAG, "createSignInIntent: launching sign-in intent");
        
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        //To set a picture on the login page
                        //.setLogo(R.drawable.)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                Log.d(TAG, "onActivityResult: successful login");
            } else {
                if (response == null) {
                    // User canceled sign in
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.sign_in_cancelled)
                            , Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onActivityResult: user canceled login");
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection)
                            , Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onActivityResult: no internet connection for login");
                } else {
                    Log.d(TAG, "onActivityResult: unknown login error");
                }
            }

        } else {
            Log.d(TAG, "onActivityResult: incorrect requestCode for sign-in");
        }

        setResult(RESULT_OK, new Intent());
        finish();
    }
}
