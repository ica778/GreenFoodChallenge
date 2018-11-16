package com.soyiz.greenfoodchallenge;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcoFragment extends Fragment {

    private Spinner spinnerShowDifferentDiets;
    private ArrayAdapter adapter;
    private PieChart pieChartDietProportionView;
    private List<Integer> colorsToChooseFrom;
    DietsCreator dietsCreator;
    private Diet myDiet;
    private Diet highMeat;
    private Diet lowMeat;
    private Diet onlyFish;
    private Diet vegetarian;
    private Diet vegan;
    private DietComparer dietComparer;

    public EcoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        colorsToChooseFrom = new ArrayList<>();
        colorsToChooseFrom.add(Color.rgb(204, 102, 0));
        colorsToChooseFrom.add(Color.rgb(204, 0, 102));
        colorsToChooseFrom.add(Color.rgb(0, 204, 204));
        colorsToChooseFrom.add(Color.rgb(0, 153, 153));
        colorsToChooseFrom.add(Color.rgb(255, 0, 0));
        colorsToChooseFrom.add(Color.rgb(255, 153, 255));
        colorsToChooseFrom.add(Color.rgb(128, 255, 0));

        dietsCreator = new DietsCreator();
        myDiet = new Diet();
        highMeat = dietsCreator.createMeatEaterDiet();
        lowMeat = dietsCreator.createLowMeatDiet();
        onlyFish = dietsCreator.createOnlyFishDiet();
        vegetarian = dietsCreator.createVegetarianDiet();
        vegan = dietsCreator.createVeganDiet();
        dietComparer = new DietComparer();

        pieChartDietProportionView = view.findViewById(R.id.pieChartDietEcoProportions);
        spinnerActions(view);
    }

    private void createDiet() {
        myDiet.setProteinPercent(ProteinSource.Beef, 0);
        myDiet.setProteinPercent(ProteinSource.Pork, 0);
        myDiet.setProteinPercent(ProteinSource.Chicken, 0);
        myDiet.setProteinPercent(ProteinSource.Fish, 0);
        myDiet.setProteinPercent(ProteinSource.Eggs, 0);
        myDiet.setProteinPercent(ProteinSource.Beans, 0);
        myDiet.setProteinPercent(ProteinSource.Vegetables, 0);

        if (UserDietInfo.getInstance().getTotalAmountOfProteinGrams() > 0) {
            int totalAmountOfProtein = UserDietInfo.getInstance().getTotalAmountOfProteinGrams();
            myDiet.setProteinPercent(ProteinSource.Beef, UserDietInfo.getInstance().getAmountOfProteinGrams("beef") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Pork, UserDietInfo.getInstance().getAmountOfProteinGrams("pork") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Chicken, UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Fish, UserDietInfo.getInstance().getAmountOfProteinGrams("fish") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Eggs, UserDietInfo.getInstance().getAmountOfProteinGrams("egg") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Beans, UserDietInfo.getInstance().getAmountOfProteinGrams("bean") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Vegetables, UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") * 100 / totalAmountOfProtein);
        }
        dietsCreator.setUserDiet(myDiet);
    }

    private void createDietEcoProportionsPieChart(int typeOfDiet) {

        pieChartDietProportionView.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        // Set click events
        pieChartDietProportionView.setRotationEnabled(false);

        // Set draw hole
        pieChartDietProportionView.setDrawHoleEnabled(false);
        pieChartDietProportionView.setCenterText("");

        // Set things outside of pie chart
        Legend legend = pieChartDietProportionView.getLegend();
        legend.setEnabled(true);
        legend.setWordWrapEnabled(true);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        pieChartDietProportionView.getDescription().setText("");

        String[] xData = {
                "Beef",
                "Chicken",
                "Pork",
                "Fish",
                "Bean",
                "Vegetable",
                "Egg"
        };

        List<Float> yData = new ArrayList<>();

        if (typeOfDiet == 1) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            }
            else {
                yData.add(0f);
                yData.add(0f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
            }
        }
        else if (typeOfDiet == 2) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            }
            else {
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
            }
        }
        else if (typeOfDiet == 3) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            }
            else {
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
            }
        }
        else if (typeOfDiet == 4) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            }
            else {
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(122.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
            }
        }
        else if (typeOfDiet == 5) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            }
            else {
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
                yData.add(12.1f);
            }
        }
        else {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            }
            else {
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinGrams("beef"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinGrams("chicken"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinGrams("pork"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinGrams("fish"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinGrams("bean"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinGrams("egg"));
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Your Current Diet");
            }
        }

        ArrayList<PieEntry> yEntries = new ArrayList<>();

        // Put values into entries that can be put into the pie chart
        for (int i = 0; i < yData.size(); i++) {
            if (yData.get(i) > 0) {
                yEntries.add(new PieEntry(yData.get(i), xData[i]));
            }
        }

        PieDataSet dietProportionsPieDataSet = new PieDataSet(yEntries, "");

        // Set pie chart and its slices
        pieChartDietProportionView.setUsePercentValues(true);
        dietProportionsPieDataSet.setSliceSpace(1f);
        dietProportionsPieDataSet.setColors(colorsToChooseFrom);

        // Set text in pie chart
        PieData pieData = new PieData(dietProportionsPieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        // Set y axis text color
        dietProportionsPieDataSet.setValueTextColor(Color.BLACK);
        dietProportionsPieDataSet.setValueTextSize(10f);

        // Set x axis text color
        pieChartDietProportionView.setEntryLabelColor(Color.BLACK);
        pieChartDietProportionView.setEntryLabelTextSize(10f);

        // Show x axis text
        pieChartDietProportionView.setDrawEntryLabels(false);

        // Show pie chart
        pieChartDietProportionView.setData(pieData);
        pieChartDietProportionView.invalidate();
    }

    // Handles events on spinner
    private void spinnerActions(View view) {
        adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.types_of_diet,
                android.R.layout.simple_spinner_item);
        spinnerShowDifferentDiets = view.findViewById(R.id.spinnerShowDifferentDiets);
        spinnerShowDifferentDiets.setAdapter(adapter);
        spinnerShowDifferentDiets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View viewClicked, int position, long id) {
                createDietEcoProportionsPieChart(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}


/*
public class EcoFragment extends Fragment {

    public EcoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ecoView = inflater.inflate(R.layout.fragment_eco, container, false);

        //TextViews for Eco information display.
        final TextView dietTextView = ecoView.findViewById(R.id.dietInfo);
        final TextView highMeatBetterPercentageView = ecoView
                .findViewById(R.id.highMeatBetterPercentage);
        final TextView lowMeatBetterPercentageView = ecoView
                .findViewById(R.id.lowMeatBetterPercentage);
        final TextView onlyFishBetterPercentageView = ecoView
                .findViewById(R.id.onlyFishBetterPercentage);
        final TextView vegetarianBetterPercentageView = ecoView
                .findViewById(R.id.vegetarianPercentage);
        final TextView veganBetterPercentageView = ecoView.findViewById(R.id.veganBetterPercentage);

        //Buttons in Econ fragment
        Button highMeatButton = ecoView.findViewById(R.id.highMeatButton);
        Button lowMeatButton = ecoView.findViewById(R.id.loweMeatButton);
        Button onlyFishButton = ecoView.findViewById(R.id.onlyFishButton);
        Button vegetarianButton = ecoView.findViewById(R.id.vegetarianButton);
        Button veganButton = ecoView.findViewById(R.id.veganButton);
        DietsCreator dietsCreator = new DietsCreator();

        //Test User Diet
        final Diet myDiet = new Diet();
        myDiet.setProteinPercent(ProteinSource.Beef, 0);
        myDiet.setProteinPercent(ProteinSource.Pork, 0);
        myDiet.setProteinPercent(ProteinSource.Chicken, 0);
        myDiet.setProteinPercent(ProteinSource.Fish, 0);
        myDiet.setProteinPercent(ProteinSource.Eggs, 0);
        myDiet.setProteinPercent(ProteinSource.Beans, 0);
        myDiet.setProteinPercent(ProteinSource.Vegetables, 0);

        if (UserDietInfo.getInstance().getTotalAmountOfProteinGrams() > 0) {
            int totalAmountOfProtein = UserDietInfo.getInstance().getTotalAmountOfProteinGrams();
            myDiet.setProteinPercent(ProteinSource.Beef, UserDietInfo.getInstance().getAmountOfProteinGrams("beef") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Pork, UserDietInfo.getInstance().getAmountOfProteinGrams("pork") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Chicken, UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Fish, UserDietInfo.getInstance().getAmountOfProteinGrams("fish") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Eggs, UserDietInfo.getInstance().getAmountOfProteinGrams("egg") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Beans, UserDietInfo.getInstance().getAmountOfProteinGrams("bean") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Vegetables, UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") * 100 / totalAmountOfProtein);
        }
        dietsCreator.setUserDiet(myDiet);

        //Default Diets in Eco Fragment
        final Diet highMeat = dietsCreator.createMeatEaterDiet();
        final Diet lowMeat = dietsCreator.createLowMeatDiet();
        final Diet onlyFish = dietsCreator.createOnlyFishDiet();
        final Diet vegetarian = dietsCreator.createVegetarianDiet();
        final Diet vegan = dietsCreator.createVeganDiet();
        final DietComparer dietComparer = new DietComparer();

        //textViews for showing better percentage in Econ Fragment
        highMeatBetterPercentageView
                .setText(dietComparer.compareCO2ePercent(myDiet, highMeat) + " %");
        lowMeatBetterPercentageView
                .setText(dietComparer.compareCO2ePercent(myDiet, lowMeat) + " %");
        onlyFishBetterPercentageView
                .setText(dietComparer.compareCO2ePercent(myDiet, onlyFish) + " %");
        vegetarianBetterPercentageView
                .setText(dietComparer.compareCO2ePercent(myDiet, vegetarian) + " %");
        veganBetterPercentageView.setText(dietComparer.compareCO2ePercent(myDiet, vegan) + " %");

        // Button listeners for Eco Fragment
        highMeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.highMeatButtonInfo, Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getActivity(), R.string.highMeatButtonChange, Toast.LENGTH_SHORT)
                        .show();
                if (UserDietInfo.getInstance().getTotalAmountOfProteinGrams() > 0)
                    dietTextView.setText(dietComparer.getChangeReport(myDiet, highMeat));
            }
        });

        lowMeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.lowMeatButtonInfo, Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getActivity(), R.string.lowMeatButtonChange, Toast.LENGTH_LONG)
                        .show();
                if (UserDietInfo.getInstance().getTotalAmountOfProteinGrams() > 0)
                    dietTextView.setText(dietComparer.getChangeReport(myDiet, lowMeat));
            }
        });
        onlyFishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.onlyFishButtonInfo, Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getActivity(), R.string.onlyFishButtonChange, Toast.LENGTH_SHORT)
                        .show();
                if (UserDietInfo.getInstance().getTotalAmountOfProteinGrams() > 0)
                    dietTextView.setText(dietComparer.getChangeReport(myDiet, onlyFish));
            }
        });
        vegetarianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.vegetarianButtonInfo, Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getActivity(), R.string.vegetarianButtonChange, Toast.LENGTH_SHORT)
                        .show();
                if (UserDietInfo.getInstance().getTotalAmountOfProteinGrams() > 0)
                    dietTextView.setText(dietComparer.getChangeReport(myDiet, vegetarian));
            }
        });
        veganButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.veganButtonInfo, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), R.string.veganButtonChange, Toast.LENGTH_SHORT)
                        .show();
                if (UserDietInfo.getInstance().getTotalAmountOfProteinGrams() > 0)
                    dietTextView.setText(dietComparer.getChangeReport(myDiet, vegan));
            }
        });
        return ecoView;
    }
}
 */
