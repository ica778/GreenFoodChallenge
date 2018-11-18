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
import com.google.firebase.firestore.util.Consumer;

public class UserFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "UserFragment";

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

        Log.d(TAG, "onCreateView: UserFragment creating");

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

        setInitialEditTextValues();

//        //TODO: example implementation, should be updated to match how the rest is implemented
//        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus == false) {
//                    String newValue = etFirstName.getText().toString();
//                    Log.d("MainActivity", "onFocusChange: new field value: '" + newValue + "'");
//
//                    (new FirebaseHelper()).getFunctions().setUserField(FirebaseHelper.Firestore.FIRST_NAME, etFirstName.getText().toString());
//                }
//            }
//        });

        setupFocusListener(etFirstName);
        setupFocusListener(etLastName);
        setupFocusListener(etAlias);
        setupFocusListener(etBio);
    }

    private void setInitialEditTextValues() {
        etFirstName.setText(User.getCurrent().getFirstName(), TextView.BufferType.EDITABLE);
        etLastName.setText(User.getCurrent().getLastName(), TextView.BufferType.EDITABLE);
        etAlias.setText(User.getCurrent().getAlias(), TextView.BufferType.EDITABLE);
        etBio.setText(User.getCurrent().getBio(), TextView.BufferType.EDITABLE);
    }

    private void setupFocusListener(EditText editText) {
        Consumer<String> function;
        User user = User.getCurrent();

        if (editText == etFirstName) {
            function = user::setFirstName;
        } else if (editText == etLastName) {
            function = user::setLastName;
        } else if (editText == etAlias) {
            function = user::setAlias;
        } else if (editText == etBio) {
            function = user::setBio;
        } else {
            Log.e(TAG, "initView: setting listener on EditText broke!");
            return;
        }

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newValue = "";

                    if (editText == etFirstName) {
                        newValue = etFirstName.getText().toString();
                    } else if (editText == etLastName) {
                        newValue = etLastName.getText().toString();
                    } else if (editText == etAlias) {
                        newValue = etAlias.getText().toString();
                    } else if (editText == etBio) {
                        newValue = etBio.getText().toString();
                    }

                    function.accept(newValue);
                }
            }
        });
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

//        //TODO passing when sign in
//        User user = new User();
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        user.setFirebaseUser(firebaseUser);
//        user.setUserDocument(new HashMap<String, Object>());
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setAlias(alias);
//        user.setCity(city);
//
//        FirebaseHelper helper = new FirebaseHelper();
//        helper.getFirestore().getUserTemplate();
//        helper.getFirestore().pushUserDocument(user);


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
