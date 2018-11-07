package com.soyiz.greenfoodchallenge;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button signInButton;
    private Button signOutButton;
    private Button deleteUserButton;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        signInButton = view.findViewById(R.id.sign_in_btn);
        signInButton.setOnClickListener(this);
        signOutButton = view.findViewById(R.id.sign_out_btn);
        signOutButton.setOnClickListener(this);
        deleteUserButton = view.findViewById(R.id.delete_user_btn);
        deleteUserButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_btn) {
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        if (view.getId() == R.id.sign_out_btn) {
            AuthUI.getInstance()
                    .signOut(getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }
                    });
        }

        if (view.getId() == R.id.delete_user_btn) {
            AuthUI.getInstance()
                    .delete(getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                            startActivity(new Intent(getActivity(),
                                    LoginActivity.class));
                            getActivity().finish();
                        }
                    });
        }
    }
}
