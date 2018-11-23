package com.soyiz.greenfoodchallenge;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Diet {
    private static final String TAG = "Diet";

    private Map<ProteinSource, Double> proteinPercentMap;

    Diet() {
        proteinPercentMap = new HashMap<>();
    }

    // Sets the percentage of a given protein
    public void setProteinPercent(ProteinSource protein, double percent) {
        proteinPercentMap.put(protein, percent);
    }

    // Gets the percentage of a given protein
    public double getProteinPercent(ProteinSource protein) {
        return proteinPercentMap.get(protein);
    }

    // Gets the weekly CO2e usage for the diet
    public double getWeeklyCO2e() {
        double output = 0;

        // Weekly serving in grams for protein
        double weeklyServing = ProteinSource.getDailyServing() * 7;
        for (Map.Entry<ProteinSource, Double> pair : proteinPercentMap.entrySet()) {

            // Weekly serving in grams for a specific protein
            double proteinWeeklyServing = pair.getValue() / 100 * weeklyServing;

            // Puts the serving into KG and multiplies by CO2e for the protein
            output += (proteinWeeklyServing / 1000.0) * pair.getKey().getCO2e();
        }
        return output;
    }

    // Gets the yearly CO2e usage for the diet
    public double getYearlyCO2e() {
        // There are 52 weeks in a year
        return getWeeklyCO2e() * 52;
    }
}
