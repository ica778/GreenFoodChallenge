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
    private TextView mActionOk, heading;
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

        recyclerView = view.findViewById(R.id.pledge_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(recyclerView.getContext());
        //so latest additions shown at the top
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        RecyclerViewAdapeterForPledge adapter = new RecyclerViewAdapeterForPledge(pledgeCardList);
        recyclerView.setAdapter(adapter);
        creatTestPledges();
        return view;
    }

    public void creatTestPledges() {
        PledgeCard test1 = new PledgeCard();
        test1.setUserName("ZC");
        test1.setPledgeAmount(777);
        pledgeCardList.add(test1);
        PledgeCard test2 = new PledgeCard();
        test2.setUserName("YY");
        test2.setPledgeAmount(777);
        pledgeCardList.add(test2);
        PledgeCard test3 = new PledgeCard();
        test3.setUserName("SC");
        test3.setPledgeAmount(777);
        pledgeCardList.add(test3);
        PledgeCard test4 = new PledgeCard();
        test4.setUserName("IC");
        test4.setPledgeAmount(777);
        pledgeCardList.add(test4);
        PledgeCard test5 = new PledgeCard();
        test5.setUserName("OSS");
        test5.setPledgeAmount(777);
        pledgeCardList.add(test5);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
