package com.soyiz.greenfoodchallenge;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// TODO: code is very messy and needs to be cleaned up. Should fix repetitive code and move some methods in here to another class
// TODO: CHECK LOGIC IN CALCULATING EMISSIONS

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorResultsFragment extends Fragment {

    private PieChart dietProportionsPieChartView;
    private PieChart dietC02ePercentsPieChartView;
    private BarChart barChartCompareEmissionsView;
    private List<Integer> colorsToChooseFrom;
    private TextView textView1, textView2, textView3;

    public CalculatorResultsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator_results, container, false);
        initView(view);
        setStringsToTextViews();
        createDietProportionsPieChart();
        createDietC02ePercents();
        createC02eEmissionsComparedToAverageBarChart();
        return view;
    }

    private void initView(View view) {
        dietProportionsPieChartView = view.findViewById(R.id.pieChartDietProportions);
        dietC02ePercentsPieChartView = view.findViewById(R.id.pieChartDietC02ePercents);
        barChartCompareEmissionsView = view.findViewById(R.id.barChartCompareEmissions);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);

        colorsToChooseFrom = new ArrayList<>();
        colorsToChooseFrom.add(Color.rgb(204, 102, 0));
        colorsToChooseFrom.add(Color.rgb(204, 0, 102));
        colorsToChooseFrom.add(Color.rgb(0, 204, 204));
        colorsToChooseFrom.add(Color.rgb(0, 153, 153));
        colorsToChooseFrom.add(Color.rgb(255, 0, 0));
        colorsToChooseFrom.add(Color.rgb(255, 153, 255));
        colorsToChooseFrom.add(Color.rgb(128, 255, 0));
    }

    // handles all text the user will see in the textviews
    private void setStringsToTextViews() {
        String textView1Text = String.format(
                getResources().getString(R.string.calculator_text1)
        );
        String textView2Text = String.format(
                getResources().getString(R.string.calculator_text2)
        );
        textView2.setText(textView2Text);

        // Average emission per capita in Metro Vancouver is 7.7 tonnes. 20% of these emissions is from food
        float averageC02eEmissionPerCapitaInMetroVancouverTonnes = 7.7f * 0.20f;

        String textView3Text = String.format(
                getResources().getString(R.string.calculator_text3),
                DietComparer.getHowManyTonnesOfC02eAYear(),
                DietComparer.getLitresOfGasolineEquivalentToDietC02e((float)DietComparer.getHowManyKGOfC02eAYear()),
                DietComparer.getHowWellC02eComparesToAverage(
                        DietComparer.getHowManyKGOfC02eAYear(),
                        averageC02eEmissionPerCapitaInMetroVancouverTonnes * 1000),
                averageC02eEmissionPerCapitaInMetroVancouverTonnes
        );
        textView3.setText(textView3Text);


    }

    // Creates pie chart showing C02e footprint of each protein
    private void createDietC02ePercents() {
        //Pie chart will show these things
        float totalC02eFootprintGrams = ((UserDietInfo.getInstance().getAmountOfProteinGrams("beef") * 27) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") * 12.1f) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("pork") * 6.9f) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("fish") * 6.1f) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("bean") * 4.1f) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") * 2) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("egg") * 2));
        Float[] yData = {
                (100 * (UserDietInfo.getInstance().getAmountOfProteinGrams("beef") * 27)) / totalC02eFootprintGrams,
                (100 * (UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") * 12.1f)) / totalC02eFootprintGrams,
                (100 * (UserDietInfo.getInstance().getAmountOfProteinGrams("pork") * 6.9f)) / totalC02eFootprintGrams,
                (100 * (UserDietInfo.getInstance().getAmountOfProteinGrams("fish") * 6.1f)) / totalC02eFootprintGrams,
                (100 * (UserDietInfo.getInstance().getAmountOfProteinGrams("bean") * 4.1f)) / totalC02eFootprintGrams,
                (100 * (UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") * 2)) / totalC02eFootprintGrams,
                (100 * (UserDietInfo.getInstance().getAmountOfProteinGrams("egg") * 2)) / totalC02eFootprintGrams
        };
        String[] xData = {
                "Beef",
                "Chicken",
                "Pork",
                "Fish",
                "Bean",
                "Vegetable",
                "Egg"
        };

        ArrayList<PieEntry> yEntries = new ArrayList<>();

        // Put values into entries that can be put into the pie chart
        for (int i = 0; i < yData.length; i++) {
            if(yData[i] > 0) {
                yEntries.add(new PieEntry(yData[i], xData[i]));
            }
        }

        PieDataSet dietC02ePercentsPieDataSet = new PieDataSet(yEntries, "");

        dietC02ePercentsPieChartView.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        // Set click events
        dietC02ePercentsPieChartView.setRotationEnabled(false);
        dietC02ePercentsPieChartView.setDrawHoleEnabled(false);

        // Set things outside of pie chart
        Legend legend = dietC02ePercentsPieChartView.getLegend();
        legend.setEnabled(true);
        legend.setWordWrapEnabled(true);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        dietC02ePercentsPieChartView.getDescription().setText("");

        // Set pie chart and its slices
        dietC02ePercentsPieChartView.setUsePercentValues(true);
        dietC02ePercentsPieDataSet.setSliceSpace(1f);
        dietC02ePercentsPieDataSet.setColors(colorsToChooseFrom);

        // Set text in pie chart
        PieData pieData = new PieData(dietC02ePercentsPieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        // Set y axis text color
        dietC02ePercentsPieDataSet.setValueTextColor(Color.BLACK);
        dietC02ePercentsPieDataSet.setValueTextSize(10f);

        // Set x axis text color
        dietC02ePercentsPieChartView.setEntryLabelColor(Color.BLACK);
        dietC02ePercentsPieChartView.setEntryLabelTextSize(10f);

        // Show x axis text
        dietC02ePercentsPieChartView.setDrawEntryLabels(false);

        // Show pie chart
        dietC02ePercentsPieChartView.setData(pieData);
        dietC02ePercentsPieChartView.invalidate();
    }

    // Creates pie chart showing proportion of each food in diet
    private void createDietProportionsPieChart() {
        float totalProteinGrams = UserDietInfo.getInstance().getTotalAmountOfProteinGrams();

        // Pie chart will show these things
        Float[] yData = {
                (UserDietInfo.getInstance().getAmountOfProteinGrams("beef") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("pork") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("fish") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("bean") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("egg") / totalProteinGrams) * 100
        };
        String[] xData = {
                "Beef",
                "Chicken",
                "Pork",
                "Fish",
                "Bean",
                "Vegetable",
                "Egg"
        };

        ArrayList<PieEntry> yEntries = new ArrayList<>();

        // Put values into entries that can be put into the pie chart
        for (int i = 0; i < yData.length; i++) {
            if(yData[i] > 0) {
                yEntries.add(new PieEntry(yData[i], xData[i]));
            }
        }

        PieDataSet dietProportionsPieDataSet = new PieDataSet(yEntries, "");

        dietProportionsPieChartView.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        // Set click events
        dietProportionsPieChartView.setRotationEnabled(false);
        dietProportionsPieChartView.setDrawHoleEnabled(false);

        // Set things outside of pie chart
        Legend legend = dietProportionsPieChartView.getLegend();
        legend.setEnabled(true);
        legend.setWordWrapEnabled(true);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        dietProportionsPieChartView.getDescription().setText("");

        // Set pie chart and its slices
        dietProportionsPieChartView.setUsePercentValues(true);
        dietProportionsPieDataSet.setSliceSpace(1f);
        dietProportionsPieDataSet.setColors(colorsToChooseFrom);

        // Set text in pie chart
        PieData pieData = new PieData(dietProportionsPieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        // Set y axis text color
        dietProportionsPieDataSet.setValueTextColor(Color.BLACK);
        dietProportionsPieDataSet.setValueTextSize(10f);

        // Set x axis text color
        dietProportionsPieChartView.setEntryLabelColor(Color.BLACK);
        dietProportionsPieChartView.setEntryLabelTextSize(10f);

        // Show x axis text
        dietProportionsPieChartView.setDrawEntryLabels(false);

        // Show pie chart
        dietProportionsPieChartView.setData(pieData);
        dietProportionsPieChartView.invalidate();
    }

    private void createC02eEmissionsComparedToAverageBarChart() {
        float tonnesOfC02eFromUser = (float) DietComparer.getHowManyTonnesOfC02eAYear();
        float averageC02eFromDietForRegion = 7.7f * 0.2f;

        // Bar chart will show these two things
        Float[] yData = {
                tonnesOfC02eFromUser,
                averageC02eFromDietForRegion
        };

        String[] xData = {
                "Your C02e Emissions",
                "Metro Vancouver Average"
        };

        ArrayList<BarEntry> yEntries = new ArrayList<>();

        // Put values into entries that can be put into the pie chart
        for (int i = 0; i < yData.length; i++) {
            yEntries.add(new BarEntry(i, yData[i]));
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
        barChartCompareEmissionsView.setScaleYEnabled(false);

        // Set bar chart bar colors
        compareEmissionsDataSet.setColor(Color.GREEN);

        // Set y axis
        YAxis yAxisLeft = barChartCompareEmissionsView.getAxisLeft();
        yAxisLeft.setStartAtZero(true);
        yAxisLeft.setAxisMaximum(3.5f);
        yAxisLeft.setDrawGridLines(true);
        YAxis yAxisRight = barChartCompareEmissionsView.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawGridLines(false);

        // Set x axis values
        XAxis xAxis = barChartCompareEmissionsView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xData));
        xAxis.setLabelCount(2);

        // Show bar chart
        barChartCompareEmissionsView.setData(barData);
        barChartCompareEmissionsView.invalidate();
    }

    // Class needed for putting strings in x axis
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
