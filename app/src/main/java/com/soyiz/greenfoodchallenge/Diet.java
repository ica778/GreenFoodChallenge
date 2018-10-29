package com.soyiz.greenfoodchallenge;

import java.util.HashMap;
import java.util.Map;

public class Diet {

    private Map<ProteinSource, Float> proteinPercentMap;

    Diet() {
        proteinPercentMap = new HashMap<>();
    }

    // Sets the percentage of a given protein
    public void setProteinPercent(ProteinSource protein, float percent) {
        proteinPercentMap.put(protein, percent);
    }

    // Gets the percentage of a given protein
    public float getProteinPercent(ProteinSource protein) {
        return proteinPercentMap.get(protein);
    }

    // Gets the weekly CO2e usage for the diet
    public float getWeeklyCO2e() {
        float output = 0;

        // Weekly serving in grams for protein
        float weeklyServing = ProteinSource.getDailyServing() * 7;
        for (Map.Entry<ProteinSource, Float> pair : proteinPercentMap.entrySet()) {

            // Weekly serving in grams for a specific protein
            float proteinWeeklyServing = pair.getValue() * weeklyServing;

            // Puts the serving into KG and multiplies by CO2e for the protein
            output += (proteinWeeklyServing / 1000f) * pair.getKey().getCO2e();
        }
        return output;
    }

    // Gets the yearly CO2e usage for the diet
    public float getYearlyCO2e() {
        // There are 52 weeks in a year
        return getWeeklyCO2e() * 52;
    }
}
