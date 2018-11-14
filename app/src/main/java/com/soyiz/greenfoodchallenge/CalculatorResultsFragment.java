package com.soyiz.greenfoodchallenge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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

        return view;
    }

    private void initView(View view) {
        dietProportionsPieChartView = view.findViewById(R.id.pieChartDietProportions);
    }

    private void createDietC02ePercents(View view) {

        float totalC02eFootprintKG = ((UserDietInfo.getInstance().getAmountOfProteinGrams("beef") * 27) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") * 12.1f) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("pork") * 6.9f) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("fish") * 6.1f) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("bean") * 4.1f) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") * 2) +
                (UserDietInfo.getInstance().getAmountOfProteinGrams("egg") * 2) / 1000);

        float[] yData = {
                ((UserDietInfo.getInstance().getAmountOfProteinGrams("beef") * 27) / 1000) / totalC02eFootprintKG,
                ((UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") * 12.1f) / 1000) / totalC02eFootprintKG,
                ((UserDietInfo.getInstance().getAmountOfProteinGrams("pork") * 6.9f) / 1000) / totalC02eFootprintKG,
                ((UserDietInfo.getInstance().getAmountOfProteinGrams("fish") * 6.1f) / 1000) / totalC02eFootprintKG,
                ((UserDietInfo.getInstance().getAmountOfProteinGrams("bean") * 4.1f) / 1000) / totalC02eFootprintKG,
                ((UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") * 2) / 1000) / totalC02eFootprintKG,
                ((UserDietInfo.getInstance().getAmountOfProteinGrams("egg") * 2) / 1000) / totalC02eFootprintKG
        };
        String[] xData = {
                "beef",
                "chicken",
                "pork",
                "fish",
                "bean",
                "vegetable",
                "egg"
        };

        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntries.add(new PieEntry(yData[i], i));
        }
        for (int i = 1; i < xData.length; i++) {
            xEntries.add(xData[i]);
        }

        PieDataSet dietC02ePercentsPieDataSet = new PieDataSet(yEntries, "Protein Quantity");
        dietC02ePercentsPieDataSet.setSliceSpace(1);
        dietC02ePercentsPieDataSet.setValueTextSize(15);
        Description description = new Description();
        description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        description.setText("Protein proportions %");
        dietC02ePercentsPieChartView.setDescription(description);
        dietC02ePercentsPieChartView.setCenterText("C02e Footprint Proportions");
        dietC02ePercentsPieChartView.setRotationEnabled(false);

        PieData pieData = new PieData(dietC02ePercentsPieDataSet);
        dietC02ePercentsPieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dietC02ePercentsPieChartView.setData(pieData);
        dietC02ePercentsPieChartView.invalidate();
    }

    private void createDietProportionsPieChart(View view) {
        float totalProteinGrams = UserDietInfo.getInstance().getTotalAmountOfProteinGrams();
        float[] yData = {
                (UserDietInfo.getInstance().getAmountOfProteinGrams("beef") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("chicken") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("pork") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("fish") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("bean") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable") / totalProteinGrams) * 100,
                (UserDietInfo.getInstance().getAmountOfProteinGrams("egg") / totalProteinGrams) * 100
        };
        String[] xData = {
                "beef",
                "chicken",
                "pork",
                "fish",
                "bean",
                "vegetable",
                "egg"
        };
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
        Description description = new Description();
        description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        description.setText("C02e Footprint Of Each Protein");
        dietProportionsPieChartView.setDescription(description);
        dietProportionsPieChartView.setCenterText("C02e Footprint Of Each Protein");
        dietProportionsPieChartView.setRotationEnabled(false);

        PieData pieData = new PieData(dietProportionsPieDataSet);
        dietProportionsPieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dietProportionsPieChartView.setData(pieData);
        dietProportionsPieChartView.invalidate();
    }


}
