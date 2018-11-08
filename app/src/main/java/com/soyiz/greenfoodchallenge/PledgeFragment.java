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
    private TextView spinnerMunicipalityText;

    public PledgeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pledge, container, false);

        // Inflate the layout for this fragment
        initView(view);
        return view;
    }

    private void initView(View view) {
        spinnerMunicipalityText = view.findViewById(R.id.showMunicipality);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.municipalities_spinner, android.R.layout.simple_spinner_item);
        regionShowSpinner = (Spinner) view.findViewById(R.id.selectRegionSpinner);
        regionShowSpinner.setAdapter(adapter);
        regionShowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpinnerItem = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
