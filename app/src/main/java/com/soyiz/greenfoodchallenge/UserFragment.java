package com.soyiz.greenfoodchallenge;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button updateprofile;
    private Button signOutButton;
    private Button signInButton;
    private Button aboutPageBtn;
    private ImageView ivHead;
    private EditText etFirstName, etLastName, etAlias, etBio;
    private Spinner spinnerCity;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        (new FirebaseHelper()).getFunctions().getUserInfoForDisplay(User.getCurrent().getFirebaseUser(), this);

        initView(view);
        return view;
    }

    private void initView(View view) {
        updateprofile = view.findViewById(R.id.update_profile_btn);
        updateprofile.setOnClickListener(this);
        signOutButton = view.findViewById(R.id.sign_out_btn);
        signOutButton.setOnClickListener(this);
        signInButton = view.findViewById(R.id.sign_in_btn);
        signInButton.setOnClickListener(this);
        aboutPageBtn = view.findViewById(R.id.about_page_btn);
        aboutPageBtn.setOnClickListener(this);

        ivHead = view.findViewById(R.id.iv_head);
        ivHead.setOnClickListener(this);
        etFirstName = view.findViewById(R.id.et_first_name);
        etLastName = view.findViewById(R.id.et_last_name);
        etAlias = view.findViewById(R.id.et_alias);
        etBio = view.findViewById(R.id.et_bio);
        spinnerCity = view.findViewById(R.id.spinner_city);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.update_profile_btn:
                Update();
                break;

            case R.id.sign_out_btn:
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                Log.d(MainActivity.TAG, "UserFragment: user signed out");
                                Toast.makeText(getContext(), getResources().getString(R.string.toast_sign_out)
                                        , Toast.LENGTH_SHORT).show();

                                ((MainActivity) getActivity()).startLogin();
                            }
                        });
                break;

            case R.id.sign_in_btn:
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    ((MainActivity) getActivity()).startLogin();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.toast_already_signed_in), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.about_page_btn:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;

            case R.id.iv_head:
                selectImage();
                break;

        }
    }

    private void Update() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String alias = etAlias.getText().toString();
        String city = (String) spinnerCity.getSelectedItem();
        String bio = etBio.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(getContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(getContext(), "Enter Alias", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(getContext(), "Enter City", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(bio)) {
            Toast.makeText(getContext(), "Enter Bio", Toast.LENGTH_SHORT).show();
            return;
        }


        //TODO passing when sign in
        User user = new User();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setFirebaseUser(firebaseUser);
        user.setUserDocument(new HashMap<String, Object>());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAlias(alias);
        user.setCity(city);

        FirebaseHelper helper = new FirebaseHelper();
        helper.getFirestore().getUserTemplate();
        helper.getFirestore().pushUserDocument(user);


        //getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void selectImage() {
        final Integer[] resIds = new Integer[]{R.drawable.ic_head1, R.drawable.ic_head2,
                R.drawable.ic_head3, R.drawable.ic_head4, R.drawable.ic_head5};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ListView listView = new ListView(getContext());

        listView.setAdapter(new ImageAdapter(getContext(), resIds));

        final Dialog dialog = builder
                .setTitle("select image")
                .setView(listView)
                .create();
        dialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ivHead.setImageResource(resIds[i]);
                dialog.dismiss();
            }
        });
    }


    public void setDisplayInfo(Map<String, Object> data) {
        String firstName = (String) data.get(FirebaseHelper.Firestore.FIRST_NAME);
        String lastName = (String) data.get(FirebaseHelper.Firestore.LAST_NAME);
        String alias = (String) data.get(FirebaseHelper.Firestore.ALIAS);
        String city = (String) data.get(FirebaseHelper.Firestore.CITY);
        String bio = (String) data.get(FirebaseHelper.Firestore.BIO);

//        Log.d("MainActivity", "setDisplayInfo: name from function: " + name);
        etFirstName.setText(firstName, TextView.BufferType.EDITABLE);
        etLastName.setText(lastName, TextView.BufferType.EDITABLE);
        etAlias.setText(alias, TextView.BufferType.EDITABLE);
        etBio.setText(bio, TextView.BufferType.EDITABLE);
    }

}
