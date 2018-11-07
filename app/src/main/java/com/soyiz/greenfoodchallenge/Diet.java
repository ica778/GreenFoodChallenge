package com.soyiz.greenfoodchallenge;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Diet {
    private static final String TAG = "Diet";

    private Map<ProteinSource, Float> proteinPercentMap;

    Diet() {
        proteinPercentMap = new HashMap<>();
    }

    // Used to load a map from Firebase into a diet object
    public void loadFromStringMap(Map<String, Float> map)
    {
        for (Map.Entry<String, Float> entry : map.entrySet())
        {
            ProteinSource protein = ProteinSource.stringToEnumValue(entry.getKey());

            if (protein == null)
            {
                // Yes, this is silently failing. Unfortunately that's pretty much the best we can do here.
                // If this case occurs, eventually a NRE will happen. But if we're in that scenario, we've already lost.
                Log.d(TAG, "[ERROR] loadFromStringMap: bad protein string. Got: '" + entry.getKey() + "'.");
                continue;
            }

            setProteinPercent(protein, entry.getValue());
        }
    }

    // Used to turn a diet into a string keyed map for saving with Firebase
    public Map<String, Float> exportToStringMap()
    {
        Map<String, Float> outputMap = new HashMap<>();

        for (Map.Entry<ProteinSource, Float> entry : proteinPercentMap.entrySet())
        {
            outputMap.put(entry.getKey().toString(), entry.getValue());
        }

        return outputMap;
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
