package com.soyiz.greenfoodchallenge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorResultsFragment extends Fragment {

    private PieChart dietProportions;


    public CalculatorResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator_results, container, false);
        initView(view);
        //createDietProportionsPieChart(view);


        return view;
    }

    private void initView(View view) {
        dietProportions = view.findViewById(R.id.pieChartDietProportions);
    }

    private void createDietProportionsPieChart(View view) {

    }


}
