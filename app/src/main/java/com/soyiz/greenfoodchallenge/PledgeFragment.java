package com.soyiz.greenfoodchallenge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PledgeFragment extends Fragment {

    private ListView pledgeListView;
    private Spinner regionShowSpinner;
    private ArrayAdapter adapter;
    private TextView selectAPledgeText;
    private FirestoreHelper accessPledges = new FirestoreHelper();
    private Map<String, Object> userPledgeInformation;
    private List<String> listOfPledgesToShow;

    public PledgeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pledge, container, false);

        initView(view);
        spinnerActions(view);
        populateListView();
        return view;
    }

    // assigns values to variables
    private void initView(View view) {
        selectAPledgeText = view.findViewById(R.id.showMunicipality);
        pledgeListView = (ListView) view.findViewById(R.id.listViewPledges);
        listOfPledgesToShow = new ArrayList<String>();
    }

    // Handles events on spinner
    private void spinnerActions(View view) {
        adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.municipalities_spinner,
                android.R.layout.simple_spinner_item);
        regionShowSpinner = (Spinner) view.findViewById(R.id.selectRegionSpinner);
        regionShowSpinner.setAdapter(adapter);
        regionShowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View viewClicked, int position, long id) {
                //listOfPledgesToShow.add(accessPledges.)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Put pledges on ListView
    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_view,
                listOfPledgesToShow);
        pledgeListView.setAdapter(adapter);
        registerClickCallBackListView();
    }

    // Handles click events on ListView
    private void registerClickCallBackListView() {
        pledgeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                //selectAPledgeText.setText((String) parent.getItemAtPosition(position));
            }
        });
    }

}
