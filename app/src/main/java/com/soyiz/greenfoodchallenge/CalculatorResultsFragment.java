package com.soyiz.greenfoodchallenge;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorResultsFragment extends Fragment {

    private PieChart dietProportionsPieChartView;
    private PieChart dietC02ePercentsPieChartView;

    public CalculatorResultsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator_results, container, false);
        initView(view);
        createDietProportionsPieChart(view);
        createDietC02ePercents(view);
        return view;
    }

    private void initView(View view) {
        dietProportionsPieChartView = view.findViewById(R.id.pieChartDietProportions);
        dietC02ePercentsPieChartView = view.findViewById(R.id.pieChartDietC02ePercents);
    }

    private void createDietC02ePercents(View view) {

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
        dietC02ePercentsPieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        // Set text in pie chart
        PieData pieData = new PieData(dietC02ePercentsPieDataSet);
        pieData.setValueFormatter(new PercentFormatter());

        // Set y axis text color
        dietC02ePercentsPieDataSet.setValueTextColor(Color.BLACK);
        dietC02ePercentsPieDataSet.setValueTextSize(10f);

        // Set x axis text color
        dietC02ePercentsPieChartView.setEntryLabelColor(Color.BLACK);
        dietC02ePercentsPieChartView.setEntryLabelTextSize(10f);

        // Show pie chart
        dietC02ePercentsPieChartView.setData(pieData);
        dietC02ePercentsPieChartView.invalidate();
    }

    private void createDietProportionsPieChart(View view) {
        float totalProteinGrams = UserDietInfo.getInstance().getTotalAmountOfProteinGrams();
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
        dietProportionsPieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

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
        dietC02ePercentsPieChartView.setDrawEntryLabels(true);

        // Show pie chart
        dietProportionsPieChartView.setData(pieData);
        dietProportionsPieChartView.invalidate();
    }


}
