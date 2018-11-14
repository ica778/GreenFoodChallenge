package com.soyiz.greenfoodchallenge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorResultsFragment extends Fragment {

    private PieChart dietProportionsPieChart;
    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};

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


        return view;
    }

    private void initView(View view) {
        dietProportionsPieChart = view.findViewById(R.id.pieChartDietProportions);
    }

    private void createDietProportionsPieChart(View view) {
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();


        for (int i = 0; i < yData.length; i++) {
            yEntries.add(new PieEntry(yData[i], i));
        }
        for (int i = 1; i < xData.length; i++) {
            xEntries.add(xData[i]);
        }

        PieDataSet dietProportionsPieDataSet = new PieDataSet(yEntries, "Protein Quantity");
        dietProportionsPieDataSet.setSliceSpace(1);
        dietProportionsPieDataSet.setValueTextSize(15);

        PieData pieData = new PieData(dietProportionsPieDataSet);

        dietProportionsPieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        
        dietProportionsPieChart.setData(pieData);
        dietProportionsPieChart.invalidate();

    }


}
