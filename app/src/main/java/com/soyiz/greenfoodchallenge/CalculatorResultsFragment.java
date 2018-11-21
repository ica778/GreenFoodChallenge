package com.soyiz.greenfoodchallenge;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorResultsFragment extends Fragment {

    private PieChart dietProportionsPieChart;
    private PieChart dietC02ePercentsPieChart;
    private BarChart compareEmissionsBarChart;
    private List<Integer> colorsToChooseFrom;
    private TextView textView1, textView2, textView3, textView4;

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
        dietProportionsPieChart = view.findViewById(R.id.pieChartDietProportions);
        dietC02ePercentsPieChart = view.findViewById(R.id.pieChartDietC02ePercents);
        compareEmissionsBarChart = view.findViewById(R.id.barChartCompareEmissions);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);

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
        String textView4Text = String.format(
                getResources().getString(R.string.calculator_text4)
        );
        textView1.setText(textView1Text);
        textView2.setText(textView2Text);
        textView4.setText(textView4Text);


        // Average emission per capita in Metro Vancouver is 7.7 tonnes. 20% of these emissions is from food
        float averageC02eEmissionPerCapitaInMetroVancouverTonnes = 7.7f * 0.20f;

        String textView3Text = String.format(
                getResources().getString(R.string.calculator_text3),
                DietComparer.getHowManyTonnesOfC02eAYear(),
                DietComparer.getLitresOfGasolineEquivalentToDietC02e((float) DietComparer.getHowManyKGOfC02eAYear()),
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
            if (yData[i] > 0) {
                yEntries.add(new PieEntry(yData[i], xData[i]));
            }
        }

        PieDataSet dietC02ePercentsPieDataSet = new PieDataSet(yEntries, "");

        dietC02ePercentsPieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        // Set click events
        dietC02ePercentsPieChart.setRotationEnabled(false);
        dietC02ePercentsPieChart.setDrawHoleEnabled(false);

        // Set things outside of pie chart
        Legend legend = dietC02ePercentsPieChart.getLegend();
        legend.setEnabled(true);
        legend.setWordWrapEnabled(true);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        dietC02ePercentsPieChart.getDescription().setText("");

        // Set pie chart and its slices
        dietC02ePercentsPieChart.setUsePercentValues(true);
        dietC02ePercentsPieDataSet.setSliceSpace(1f);
        dietC02ePercentsPieDataSet.setColors(colorsToChooseFrom);

        // Set text in pie chart
        PieData pieData = new PieData(dietC02ePercentsPieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        // Set y axis text color
        dietC02ePercentsPieDataSet.setValueTextColor(Color.BLACK);
        dietC02ePercentsPieDataSet.setValueTextSize(10f);

        // Set x axis text color
        dietC02ePercentsPieChart.setEntryLabelColor(Color.BLACK);
        dietC02ePercentsPieChart.setEntryLabelTextSize(10f);

        // Show x axis text
        dietC02ePercentsPieChart.setDrawEntryLabels(false);

        // Show y axis text
        dietC02ePercentsPieDataSet.setDrawValues(true);

        // Show pie chart
        dietC02ePercentsPieChart.setData(pieData);
        dietC02ePercentsPieChart.invalidate();

        dietC02ePercentsPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                double valueToShowOnClick = (double) Math.round(yData[(int)h.getX()] * 10) / 10;
                String xAxisStringToShowOnClick = xData[(int)h.getX()];
                String onValueSelectedToastString = xAxisStringToShowOnClick + ": " + valueToShowOnClick + " %";
                Toast.makeText(getContext(),  onValueSelectedToastString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
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
            if (yData[i] > 0) {
                yEntries.add(new PieEntry(yData[i], xData[i]));
            }
        }

        PieDataSet dietProportionsPieDataSet = new PieDataSet(yEntries, "");

        dietProportionsPieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        // Set click events
        dietProportionsPieChart.setRotationEnabled(false);
        dietProportionsPieChart.setDrawHoleEnabled(false);

        // Set things outside of pie chart
        Legend legend = dietProportionsPieChart.getLegend();
        legend.setEnabled(true);
        legend.setWordWrapEnabled(true);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        dietProportionsPieChart.getDescription().setText("");

        // Set pie chart and its slices
        dietProportionsPieChart.setUsePercentValues(true);
        dietProportionsPieDataSet.setSliceSpace(1f);
        dietProportionsPieDataSet.setColors(colorsToChooseFrom);

        // Set text in pie chart
        PieData pieData = new PieData(dietProportionsPieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        // Set y axis text color
        dietProportionsPieDataSet.setValueTextColor(Color.BLACK);
        dietProportionsPieDataSet.setValueTextSize(10f);

        // Set x axis text color
        dietProportionsPieChart.setEntryLabelColor(Color.BLACK);
        dietProportionsPieChart.setEntryLabelTextSize(10f);

        // Show x axis text
        dietProportionsPieChart.setDrawEntryLabels(false);

        // Show y axis text
        dietProportionsPieDataSet.setDrawValues(true);

        // Show pie chart
        dietProportionsPieChart.setData(pieData);
        dietProportionsPieChart.invalidate();

        dietProportionsPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                double valueToShowOnClick = (double) Math.round(yData[(int)h.getX()] * 10) / 10;
                String xAxisStringToShowOnClick = xData[(int)h.getX()];
                String onValueSelectedToastString = xAxisStringToShowOnClick + ": " + valueToShowOnClick + " %";
                Toast.makeText(getContext(),  onValueSelectedToastString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
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
        xAxis.setLabelCount(2);

        // Show bar chart
        compareEmissionsBarChart.setData(barData);
        compareEmissionsBarChart.invalidate();
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
