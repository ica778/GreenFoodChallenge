package com.soyiz.greenfoodchallenge;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.security.AccessController.getContext;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 111;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            createSignInIntent();
        }
    }

    public void createSignInIntent() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
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
                startActivity(new Intent (this, MainActivity.class));
                return;
            } else {
                if (response == null) {
                    // User canceled sign in
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.sign_in_cancelled)
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection)
                            ,Toast.LENGTH_SHORT);
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    return;
                }
            }
        }
    }

    //to customize theme and logo
    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                       // .setLogo(R.drawable.my_great_logo)      // Set logo drawable
                       // .setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }
}
