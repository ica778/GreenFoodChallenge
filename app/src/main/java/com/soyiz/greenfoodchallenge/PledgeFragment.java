package com.soyiz.greenfoodchallenge;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PledgeFragment extends Fragment {

    private Spinner regionShowSpinner;
    private ArrayAdapter adapter;
    private TextView showInformationAboutPledgesInMunicipality;
    private List<String> listOfPledgesToShow;
    private double totalGoalC02e;
    private int amountOfPeoplePledged;
    private Button sharing;
    private int prevSpinnerSelection = 0;

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
        return view;
    }

    // assigns values to variables
    private void initView(View view) {
        showInformationAboutPledgesInMunicipality = view.findViewById(R.id.showInformationAboutPledge);
        listOfPledgesToShow = new ArrayList<>();
        totalGoalC02e = 0.0;
        amountOfPeoplePledged = 0;
        sharing = view.findViewById(R.id.share);
        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "";
                String shareSub = "";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Sharing App"));

            }
        });
        //TODO: remove once users work right
//        (new FirebaseHelper()).getFirestore().queryPledgesForViewer(this);
    }

    // Handles events on spinner
    private void spinnerActions(View view) {


        adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.municipalities_spinner,
                android.R.layout.simple_spinner_item);
        regionShowSpinner = view.findViewById(R.id.selectRegionSpinner);
        regionShowSpinner.setAdapter(adapter);
        //Turn off showing Pledge dialog when initialize the PledgeFragment UI.
        regionShowSpinner.setSelection(0, false);
        regionShowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View viewClicked, int position, long id) {

                String selected = regionShowSpinner.getSelectedItem().toString();
                PledgeDialog dialog = PledgeDialog.newInstance(selected);
                dialog.setTargetFragment(PledgeFragment.this, 1);
                if (prevSpinnerSelection != position) {
                    dialog.show(getFragmentManager(), "MyCustomDialog");
                    prevSpinnerSelection = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String stringToShow = getResources().getString(R.string.show_pledge_information);
        // String, int, float, float, float
        String pledgeShowData = String.format(stringToShow,
                getCountOfPeoplePledged(),
                getTonnesOfC02Pledged(),
                getAmountOfGasolineInC02Saved(0),
                getAverageC02Pledged());
        showInformationAboutPledgesInMunicipality.setText(pledgeShowData);
    }

    private ArrayAdapter<String> makeAdapterForList() {
        if (getActivity() == null) {
            Log.w("PledgeFragment",
                    "makeAdapterForList: NULL THING HAPPENED AND DIDN'T BREAK (HOPEFULLY)");
            return null;
        }

        return new ArrayAdapter<>(getActivity(), R.layout.list_view, listOfPledgesToShow);
    }

    // updates listOfPledgesToShow list to have correct pledges and returns whether no municipality was chosen
    private void populateListView(String municipalityPicked) {
        if (municipalityPicked.equals("No municipality chosen")) {
            listOfPledgesToShow.clear();
            listOfPledgesToShow.add("Please Choose a municipality");


        } else {
            listOfPledgesToShow.clear();
            String individualPledgeInformation = "";
        }
    }

    // Returns string to show on list_view for pledge
    private String pledgeStringToShowOnListView(Map<String, Object> userToShow) {
        String userData = "";
        Map<String, Object> pledgeMap = (Map<String, Object>) userToShow
                .get(FirebaseHelper.Firestore.PLEDGE);

        userData = userData +
                userToShow.get(FirebaseHelper.Firestore.FIRST_NAME) +
                " " +
                userToShow.get(FirebaseHelper.Firestore.LAST_NAME) +
                ": " +
                pledgeMap.get(FirebaseHelper.Firestore.CURRENT_CO2E);
        return userData;
    }

    // Appends list in argument to listOfPledgesToShow and updates list_view
    public void appendList(List<Map<String, Object>> listToAppend) {
        for (Map<String, Object> map : listToAppend) {
            listOfPledgesToShow.add(pledgeStringToShowOnListView(map));

            Map<String, Object> pledgeMap = (Map<String, Object>) map
                    .get(FirebaseHelper.Firestore.PLEDGE);
            totalGoalC02e += (Double) pledgeMap.get(FirebaseHelper.Firestore.GOAL_CO2E);
            amountOfPeoplePledged += 1;
        }
    }

    private int getCountOfPeoplePledged() {
        return amountOfPeoplePledged;
    }

    private double getTonnesOfC02Pledged() {
        return totalGoalC02e;
    }

    private float getAmountOfGasolineInC02Saved(int amountOfC02eInKG) {
        float amountOfGasolineEquivalent = amountOfC02eInKG / 2.3f;
        return amountOfGasolineEquivalent;
    }

    private double getAverageC02Pledged() {
        return totalGoalC02e / amountOfPeoplePledged;
    }
}
