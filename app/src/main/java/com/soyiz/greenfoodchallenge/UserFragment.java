package com.soyiz.greenfoodchallenge;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button signInButton;
    private Button signOutButton;
    private Button deleteUserButton;
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
        if (view.getId() == R.id.sign_in_btn) {
            //However sign in is implemented, just put this line when called and it should take care of the rest
            Update();
            //startActivity(new Intent(getActivity(), LoginActivity.class));
            //getActivity().finish();

            ////////////////////////////////////////////////////////////////////////////////////////////
        }

        if (view.getId() == R.id.sign_out_btn) {
            //However sign out is implemented, just put these lines when called and it should take care of the rest
            AuthUI.getInstance()
                    .signOut(getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }
                    });
            /////////////////////////////////////////////////////////////////////////////////////
        }

        if (view.getId() == R.id.delete_user_btn) {
            //However delete user is implemented, just put these lines when called and it should take care of the rest
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
            ///////////////////////////////////////////////////////////////////////////////////////
        }

        if (view.getId() == R.id.about_page_btn) {
            startActivity(new Intent(getContext(), AboutActivity.class));

        }

        if (view.getId() == R.id.iv_head) {
            selectImage();
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

      FirestoreHelper helper = new FirestoreHelper();
        helper.getUserTemplate();
      helper.pushUserDocument(user);

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


}
