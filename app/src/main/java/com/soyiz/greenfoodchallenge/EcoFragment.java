package com.soyiz.greenfoodchallenge;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

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
    private TextView textViewEco2;
    private TextView textViewEco3;

    private BarChart barChartCompareEmissionsView;

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
        createDiet();
        spinnerActions(view);
        createDietEcoProportionsPieChart(0);
        createC02eEmissionsComparedToAverageBarChart(0);
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

        textViewEco2 = view.findViewById(R.id.textViewEco2);
        dietsCreator = new DietsCreator();
        myDiet = new Diet();

        pieChartDietProportionView = view.findViewById(R.id.pieChartDietEcoProportions);
        //spinnerActions(view);

        textViewEco3 = view.findViewById(R.id.textViewEco3);
        barChartCompareEmissionsView = view.findViewById(R.id.barChartCompareEmissions);
    }

    private void createDiet() {
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
            float totalAmountOfProtein = UserDietInfo.getInstance().getTotalAmountOfProteinGrams();
            myDiet.setProteinPercent(ProteinSource.Beef, UserDietInfo.getInstance().getAmountOfProteinGrams("beef") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Pork, UserDietInfo.getInstance().getAmountOfProteinGrams("pork") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Chicken, UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Fish, UserDietInfo.getInstance().getAmountOfProteinGrams("fish") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Eggs, UserDietInfo.getInstance().getAmountOfProteinGrams("egg") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Beans, UserDietInfo.getInstance().getAmountOfProteinGrams("bean") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Vegetables, UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") * 100 / totalAmountOfProtein);
        }
        dietsCreator.setUserDiet(myDiet);
        highMeat = dietsCreator.createMeatEaterDiet();
        lowMeat = dietsCreator.createLowMeatDiet();
        onlyFish = dietsCreator.createOnlyFishDiet();
        vegetarian = dietsCreator.createVegetarianDiet();
        vegan = dietsCreator.createVeganDiet();
        dietComparer = new DietComparer();
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


        // high meat diet
        if (typeOfDiet == 1) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                double A = DietComparer.getHowManyKGOfC02eAYear();
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Beef));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Pork));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Fish));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Beans));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Eggs));


            }
        }

        // low meat diet
        else if (typeOfDiet == 2) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Beef));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Pork));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Fish));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Beans));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Eggs));
            }
        }

        // only fish
        else if (typeOfDiet == 3) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Beef));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Pork));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Fish));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Beans));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Eggs));
            }
        }

        // vegetarian
        else if (typeOfDiet == 4) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Beef));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Pork));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Fish));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Beans));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Eggs));
            }
        }

        // vegan
        else if (typeOfDiet == 5) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                yData.add((float) vegan.getProteinPercent(ProteinSource.Beef));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Pork));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Fish));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Beans));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Eggs));
            }
        } else {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setDrawHoleEnabled(true);
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                textViewEco2.setText("");
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
                createC02eEmissionsComparedToAverageBarChart(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createC02eEmissionsComparedToAverageBarChart(int typeOfDiet) {
        textViewEco3.setText(getResources().getString(R.string.co2eEmissionsComparation));
        float yearlyCO2e;
        if (typeOfDiet == 1) {
            yearlyCO2e = (float) highMeat.getYearlyCO2e();
        } else if (typeOfDiet == 2) {
            yearlyCO2e = (float) lowMeat.getYearlyCO2e();
        } else if (typeOfDiet == 3) {
            yearlyCO2e = (float) onlyFish.getYearlyCO2e();
        } else if (typeOfDiet == 4) {
            yearlyCO2e = (float) vegetarian.getYearlyCO2e();
        } else if (typeOfDiet == 5) {
            yearlyCO2e = (float) vegan.getYearlyCO2e();
        } else {
            textViewEco3.setText("");
            barChartCompareEmissionsView.setNoDataText("Please choose a Eco Diet");
            return;
        }
        float currentDietCO2e = (float) DietComparer.getHowManyKGOfC02eAYear();
        float averageC02eFromDietForRegion = 7.7f * 0.2f*1000;

        // Bar chart will show these two things
        ArrayList<Float> yData = new ArrayList<>();

        String[] xData = {
                "Eco CO2e",
                "Current CO2e",
                "Metro Vancouver Average"
        };
        barChartCompareEmissionsView.setDrawValueAboveBar(true);
        if (UserDietInfo.getInstance().getDietMap().size() == 0) {
            yData.clear();
            barChartCompareEmissionsView.clearValues();
            barChartCompareEmissionsView.setNoDataText("Create a Diet First");
        } else {
            yData.add(yearlyCO2e);
            yData.add(currentDietCO2e);
            yData.add(averageC02eFromDietForRegion);
        }

        ArrayList<BarEntry> yEntries = new ArrayList<>();

        // Put values into entries that can be put into the pie chart
        for (int i = 0; i < yData.size(); i++) {
            yEntries.add(new BarEntry(i, yData.get(i)));
        }

        BarDataSet compareEmissionsDataSet = new BarDataSet(yEntries, "");

        // Set things outside bar chart
        Legend legend = barChartCompareEmissionsView.getLegend();
        legend.setEnabled(false);
        barChartCompareEmissionsView.getDescription().setText("");

        BarData barData = new BarData(compareEmissionsDataSet);

        // Set bar chart axis texts
        barChartCompareEmissionsView.getXAxis().setDrawLabels(true);
        barChartCompareEmissionsView.getLegend().setEnabled(false);
        barChartCompareEmissionsView.setScaleYEnabled(true);

        // Set bar chart bar colors
        compareEmissionsDataSet.setColor(Color.GREEN);

        // Set y axis
        YAxis yAxisLeft = barChartCompareEmissionsView.getAxisLeft();
        yAxisLeft.setStartAtZero(true);
//        yAxisLeft.setAxisMaximum(103f);
        yAxisLeft.setDrawGridLines(true);
        YAxis yAxisRight = barChartCompareEmissionsView.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawGridLines(false);

        // Set x axis values
        XAxis xAxis = barChartCompareEmissionsView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xData));
        xAxis.setLabelCount(3);

        // Show bar chart
        barChartCompareEmissionsView.setData(barData);
        barChartCompareEmissionsView.invalidate();
    }

    private class MyXAxisValueFormatter implements IAxisValueFormatter {
        private String[] xValues;

        public MyXAxisValueFormatter(String[] xAxisValues) {
            this.xValues = xAxisValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return xValues[(int) value];
        }
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
