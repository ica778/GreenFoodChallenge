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

import static com.soyiz.greenfoodchallenge.FirestoreHelper.FIRST_NAME;

public class PledgeFragment extends Fragment {

    private ListView pledgeListView;
    private Spinner regionShowSpinner;
    private ArrayAdapter adapter;
    private TextView showInformationAboutPledgesInMunicipality;
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
        return view;
    }

    // assigns values to variables
    private void initView(View view) {
        showInformationAboutPledgesInMunicipality = view.findViewById(R.id.showInformationAboutPledge);
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

                // Updates scrollview to show correct pledges
                populateListView(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String stringToShow = getResources().getString(R.string.show_pledge_information);
        // String, int, float, float, float
        String pledgeShowData = String.format(stringToShow, getCountOfPeoplePledged(), getTonnesOfC02Pledged(), getAmountOfGasolineInC02Saved(0), getAverageC02Pledged() );
        showInformationAboutPledgesInMunicipality.setText(pledgeShowData);
    }

    // updates listOfPledgesToShow list to have correct pledges and returns whether no municipality was chosen
    private void populateListView(String municipalityPicked) {
        if (municipalityPicked.equals("No municipality chosen")) {
            listOfPledgesToShow.clear();
            userPledgeInformation = accessPledges.getUserTemplate();
            listOfPledgesToShow.add("Please Choose a municipality");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_view,
                    listOfPledgesToShow);
            userPledgeInformation = accessPledges.getUserTemplate();
            pledgeListView.setAdapter(adapter);
            registerClickCallBackListView();
            return;
        }
        else {
            listOfPledgesToShow.clear();
            String individualPledgeInformation = "";
            // get information from firebase

        }

        //appendList();

        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_view,
                listOfPledgesToShow);
        userPledgeInformation = accessPledges.getUserTemplate();


        pledgeListView.setAdapter(adapter);
        registerClickCallBackListView();
        */
    }


    // Appends list in argument to listOfPledgesToShow and updates list_view
    public void appendList (List<String> listToAppend) {
        listOfPledgesToShow.addAll(listToAppend);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_view,
                listOfPledgesToShow);
        userPledgeInformation = accessPledges.getUserTemplate();
        pledgeListView.setAdapter(adapter);
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

    private int getCountOfPeoplePledged() {
        return 0;
    }

    private float getTonnesOfC02Pledged() {
        return 0f;
    }

    private float getAmountOfGasolineInC02Saved(int amountOfC02eInKG) {
        float amountOfGasolineEquivalent = amountOfC02eInKG / 2.3f;
        return amountOfGasolineEquivalent;
    }

    private float getAverageC02Pledged() {
        return 0f;
    }

}
