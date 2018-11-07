package com.soyiz.greenfoodchallenge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PledgeFragment extends Fragment {

    private ListView pledgeListView;

    public PledgeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pledge, container, false);

        List<String> listOfPledges = new ArrayList<String>();
        ListAdapter pledgeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listOfPledges);
        ListView listOfAllPledges = (ListView) view.findViewById(R.id.pledgeListView);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pledge, container, false);
    }


}
