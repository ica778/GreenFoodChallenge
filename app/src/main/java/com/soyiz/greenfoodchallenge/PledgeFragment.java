package com.soyiz.greenfoodchallenge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class PledgeFragment extends Fragment {

    private ListView pledgeListView;
    private Spinner regionShowSpinner;
    private ArrayAdapter adapter;
    private TextView selectAPledgeText;

    public PledgeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pledge, container, false);

        // Inflate the layout for this fragment
        initView(view);
        spinnerActions(view);
        populateListView();
        return view;
    }

    private void initView(View view) {
        selectAPledgeText = view.findViewById(R.id.showMunicipality);
        pledgeListView = (ListView) view.findViewById(R.id.listViewPledges);
    }

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
                String selectedSpinnerItem = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateListView() {
        List<String> listOfPledges = new ArrayList<>();
        listOfPledges.add("blue");
        listOfPledges.add("red");
        listOfPledges.add("green");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_view,
                listOfPledges);
        pledgeListView.setAdapter(adapter);
        registerClickCallBackListView();
    }

    private void registerClickCallBackListView() {
        pledgeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                selectAPledgeText.setText((String) parent.getItemAtPosition(position));
            }
        });
    }

}
