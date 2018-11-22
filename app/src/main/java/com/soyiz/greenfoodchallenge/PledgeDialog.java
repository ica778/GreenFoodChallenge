package com.soyiz.greenfoodchallenge;

import android.support.v4.app.DialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

import java.util.Map;
import com.google.firebase.firestore.util.Consumer;


public class PledgeDialog extends DialogFragment {

    private static final String TAG = "PledgeDialog";




    //widgets
    private EditText mInput;
    private TextView mActionOk,heading;
    private RecyclerView recyclerView = null;
    private List<PledgeCard> pledgeCardList = new ArrayList<>();

    public static PledgeDialog newInstance(String title) {
        PledgeDialog frag = new PledgeDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pledge, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        heading = view.findViewById(R.id.heading);
        String title = getArguments().getString("title");
        heading.setText(title);

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        //set Pledge
        Map<String, Object> pledge = new HashMap<>();
        pledge.put("currentCO2e", 0.0);
        pledge.put("goalCO2e", 20.0);

        FirebaseHelper.Functions functions = (new FirebaseHelper()).getFunctions();
        functions.setUserField(FirebaseHelper.Firestore.PLEDGE, pledge);


        recyclerView = view.findViewById(R.id.pledge_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(recyclerView.getContext());
        //so latest additions shown at the top
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        RecyclerViewAdapeterForPledge adapter = new RecyclerViewAdapeterForPledge(pledgeCardList);
        recyclerView.setAdapter(adapter);









        return view;
    }



}















