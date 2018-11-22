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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EcoFragment extends Fragment implements View.OnClickListener {

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
    private TextView textView3;
    private float newDietC02eEmissionsGoal;
    private Button pledgeFirebaseButton;
    private boolean userHasSelectedNewDiet;
    private User user;
    private int positionOfSpinnerSelected;

    private BarChart compareEmissionsBarChart;

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

        newDietC02eEmissionsGoal = 0;
        userHasSelectedNewDiet = false;

        textViewEco2 = view.findViewById(R.id.textViewEco2);
        dietsCreator = new DietsCreator();
        myDiet = new Diet();

        pieChartDietProportionView = view.findViewById(R.id.pieChartDietEcoProportions);
        textView3 = view.findViewById(R.id.textViewEco3);
        compareEmissionsBarChart = view.findViewById(R.id.barChartCompareEmissions);
        pledgeFirebaseButton = view.findViewById(R.id.buttonEcoFirebase);
        pledgeFirebaseButton.setOnClickListener(this);
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

        if (UserDietInfo.getInstance().getTotalAmountOfProteinKG() > 0) {
            float totalAmountOfProtein = (float) UserDietInfo.getInstance().getTotalAmountOfProteinKG();
            myDiet.setProteinPercent(ProteinSource.Beef, UserDietInfo.getInstance().getAmountOfProteinKG("beef") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Pork, UserDietInfo.getInstance().getAmountOfProteinKG("pork") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Chicken, UserDietInfo.getInstance().getAmountOfProteinKG("chicken") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Fish, UserDietInfo.getInstance().getAmountOfProteinKG("fish") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Eggs, UserDietInfo.getInstance().getAmountOfProteinKG("egg") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Beans, UserDietInfo.getInstance().getAmountOfProteinKG("bean") * 100 / totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Vegetables, UserDietInfo.getInstance().getAmountOfProteinKG("vegetable") * 100 / totalAmountOfProtein);
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
        pieChartDietProportionView.setDrawHoleEnabled(true);
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
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                textViewEco2.setText("Emissions Comparison (Tonnes)");
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Beef));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Pork));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Fish));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Beans));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) highMeat.getProteinPercent(ProteinSource.Eggs));
                pieChartDietProportionView.setCenterText("Meat Eater Diet");
            }
        }

        // low meat diet
        else if (typeOfDiet == 2) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                textViewEco2.setText("Emissions Comparison (Tonnes)");
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Beef));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Pork));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Fish));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Beans));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) lowMeat.getProteinPercent(ProteinSource.Eggs));
                pieChartDietProportionView.setCenterText("Low Meat Diet");
            }
        }

        // only fish
        else if (typeOfDiet == 3) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                textViewEco2.setText("Emissions Comparison (Tonnes)");
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Beef));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Pork));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Fish));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Beans));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) onlyFish.getProteinPercent(ProteinSource.Eggs));
                pieChartDietProportionView.setCenterText("Only Fish Diet");
            }
        }

        // vegetarian
        else if (typeOfDiet == 4) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                textViewEco2.setText("Emissions Comparison (Tonnes)");
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Beef));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Pork));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Fish));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Beans));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) vegetarian.getProteinPercent(ProteinSource.Eggs));
                pieChartDietProportionView.setCenterText("Vegetarian Diet");
            }
        }

        // vegan
        else if (typeOfDiet == 5) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                textViewEco2.setText("Emissions Comparison (Tonnes)");
                yData.add((float) vegan.getProteinPercent(ProteinSource.Beef));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Chicken));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Pork));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Fish));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Beans));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Vegetables));
                yData.add((float) vegan.getProteinPercent(ProteinSource.Eggs));
                pieChartDietProportionView.setCenterText("Vegan Diet");
            }
        } else {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                yData.clear();
                pieChartDietProportionView.setCenterText("Create a Diet First");
            } else {
                textViewEco2.setText("");
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinKG("beef"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinKG("chicken"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinKG("pork"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinKG("fish"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinKG("bean"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinKG("vegetable"));
                yData.add((float) UserDietInfo.getInstance().getAmountOfProteinKG("egg"));
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

        dietProportionsPieDataSet.setHighlightEnabled(false);
        pieChartDietProportionView.setHighlightPerTapEnabled(false);

        // Set text in pie chart
        PieData pieData = new PieData(dietProportionsPieDataSet);
        pieData.setValueFormatter(new PieChartValueFormatter());

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
                positionOfSpinnerSelected = position;
                createDietEcoProportionsPieChart(position);
                createC02eEmissionsComparedToAverageBarChart(position);
                if (position != 0) {
                    userHasSelectedNewDiet = true;
                } else {
                    userHasSelectedNewDiet = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createC02eEmissionsComparedToAverageBarChart(int typeOfDiet) {

        float newDietTonnesOfC02e;
        // highMeat
        if (typeOfDiet == 1) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                compareEmissionsBarChart.setNoDataText("Please complete the Diet page first");
                return;
            } else {
                newDietTonnesOfC02e = (float) highMeat.getYearlyCO2e() / 1000;
            }
        }

        // lowMeat
        else if (typeOfDiet == 2) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                compareEmissionsBarChart.setNoDataText("Please complete the Diet page first");
                return;
            } else {
                newDietTonnesOfC02e = (float) lowMeat.getYearlyCO2e() / 1000;
            }
        }

        // onlyFish
        else if (typeOfDiet == 3) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                compareEmissionsBarChart.setNoDataText("Please complete the Diet page first");
                return;
            } else {
                newDietTonnesOfC02e = (float) onlyFish.getYearlyCO2e() / 1000;
            }
        }

        // Vegetarian
        else if (typeOfDiet == 4) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                compareEmissionsBarChart.setNoDataText("Please complete the Diet page first");
                return;
            } else {
                newDietTonnesOfC02e = (float) vegetarian.getYearlyCO2e() / 1000;
            }
        }

        // Vegan
        else if (typeOfDiet == 5) {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                compareEmissionsBarChart.setNoDataText("Please complete the Diet page first");
                return;
            } else {
                newDietTonnesOfC02e = (float) vegan.getYearlyCO2e() / 1000;
            }
        }

        // this will only happen if the first spinner thing is chosen
        else {
            if (UserDietInfo.getInstance().getDietMap().size() == 0) {
                compareEmissionsBarChart.setNoDataText("Please complete the Diet page first");
                return;
            } else {
                compareEmissionsBarChart.setNoDataText("Please choose a new diet");
                return;
            }
        }

        float tonnesOfC02eFromUser = (float) DietComparer.getHowManyTonnesOfC02eAYear();
        float averageC02eFromDietForRegion = 7.7f * 0.2f;
        newDietTonnesOfC02e = newDietTonnesOfC02e * tonnesOfC02eFromUser;

        //showComparison(tonnesOfC02eFromUser * 1000f, newDietTonnesOfC02e * 1000);

        // Bar chart will show these things
        Float[] yData = {
                tonnesOfC02eFromUser,
                averageC02eFromDietForRegion,
                newDietTonnesOfC02e
        };

        String[] xData = {
                "Current Diet",
                "Metro Vancouver Average",
                "New Diet"
        };

        newDietC02eEmissionsGoal = newDietTonnesOfC02e;

        ArrayList<BarEntry> yEntries = new ArrayList<>();

        // Put values into entries that can be put into the pie chart
        for (int i = 0; i < yData.length; i++) {
            yEntries.add(new BarEntry(i, yData[i]));
        }

        BarDataSet compareEmissionsDataSet = new BarDataSet(yEntries, "");

        // Set things outside bar chart
        Legend legend = compareEmissionsBarChart.getLegend();
        legend.setEnabled(false);
        compareEmissionsBarChart.getDescription().setText("");

        BarData barData = new BarData(compareEmissionsDataSet);

        // Set bar chart axis texts
        compareEmissionsBarChart.getXAxis().setDrawLabels(true);
        compareEmissionsBarChart.getLegend().setEnabled(false);
        compareEmissionsBarChart.setScaleYEnabled(false);
        compareEmissionsBarChart.setScaleXEnabled(false);

        // Set bar chart features
        compareEmissionsDataSet.setColor(Color.GREEN);
        compareEmissionsDataSet.setHighlightEnabled(false);


        // Set y axis
        YAxis yAxisLeft = compareEmissionsBarChart.getAxisLeft();
        yAxisLeft.setStartAtZero(true);
        yAxisLeft.setAxisMaximum(2.0f + yData[0]);
        yAxisLeft.setDrawGridLines(true);
        YAxis yAxisRight = compareEmissionsBarChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawGridLines(false);

        // Set x axis values
        XAxis xAxis = compareEmissionsBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xData));
        xAxis.setLabelCount(3);

        // Show bar chart
        compareEmissionsBarChart.setData(barData);
        compareEmissionsBarChart.invalidate();
    }

    // Firebase part
    @Override
    public void onClick(View v) {
        if (newDietC02eEmissionsGoal == 0 && userHasSelectedNewDiet == true) {
            Toast.makeText(getContext(), "Your current diet is empty, please complete Diet page again", Toast.LENGTH_SHORT).show();
        } else if (UserDietInfo.getInstance().getDietMap().size() == 0) {
            Toast.makeText(getContext(), "Please complete Diet page first", Toast.LENGTH_SHORT).show();
        } else if (userHasSelectedNewDiet == false) {
            Toast.makeText(getContext(), "Select a new diet plan", Toast.LENGTH_SHORT).show();
        } else {
            user = User.getCurrent();
            if (positionOfSpinnerSelected == 1) {
                user.setGoalDiet(highMeat);
            } else if (positionOfSpinnerSelected == 2) {
                user.setGoalDiet(lowMeat);
            } else if (positionOfSpinnerSelected == 3) {
                user.setGoalDiet(onlyFish);
            } else if (positionOfSpinnerSelected == 4) {
                user.setGoalDiet(vegetarian);
            } else if (positionOfSpinnerSelected == 5) {
                user.setGoalDiet(vegan);
            }
            user.setCurrentDiet(myDiet);
            Toast.makeText(getContext(), "You have pledged this diet", Toast.LENGTH_SHORT).show();
        }
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

    // Format y axis values for pie charts
    private class PieChartValueFormatter implements IValueFormatter {
        private DecimalFormat formattedValue;

        public PieChartValueFormatter() {
            formattedValue = new DecimalFormat("###,###,###");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if (value < 3) {
                return "";
            }
            return formattedValue.format((int) value) + " %";
        }
    }

    private void showComparison(float oldDietC02eKG, float newDietC02eKG) {
        float percentImprovement = newDietC02eKG / oldDietC02eKG;
        float tonnesOfC02eSaved = (oldDietC02eKG - newDietC02eKG) / 1000f;
        float barrelsOfGasolineSaved = DietComparer.getLitresOfGasolineEquivalentToDietC02e(oldDietC02eKG) -
                DietComparer.getLitresOfGasolineEquivalentToDietC02e(newDietC02eKG);
        String stringToShow = String.format(
                getResources().getString(R.string.C02eEmissionsComparisonDescription),
                percentImprovement,
                tonnesOfC02eSaved,
                barrelsOfGasolineSaved
        );
        textView3.setText(stringToShow);
    }
}