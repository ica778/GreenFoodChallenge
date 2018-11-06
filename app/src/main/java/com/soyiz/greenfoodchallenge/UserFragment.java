package com.soyiz.greenfoodchallenge;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button logInButton;

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
        logInButton = view.findViewById(R.id.log_in_btn);
        logInButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        getActivity().startActivity(intent);
    }
}
