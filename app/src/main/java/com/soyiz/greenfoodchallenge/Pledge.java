package com.soyiz.greenfoodchallenge;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Pledge {
    private static final String TAG = "Pledge";


    private float goalCO2eSavingsTonnes = -1f;
    private float currentCO2eSavingsTonnes = -1f;

    Pledge() {
    }

    // Used to load a map from Firebase into a pledge object
    public void loadFromStringMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "currentCO2e":
                    currentCO2eSavingsTonnes = (Float) entry.getValue();
                    break;
                case "goalCO2e":
                    goalCO2eSavingsTonnes = (Float) entry.getValue();
                    break;

                default:
                    // More bad silent unexpected behaviour... But still best solution without throwing.
                    Log.d(TAG, "[ERROR] loadFromStringMap: not recognized key in map. Please update this function!");
            }
        }
    }

    // Used to turn a pledge into a string keyed map for saving with Firebase
    public Map<String, Object> exportToStringMap() {
        Map<String, Object> outputMap = new HashMap<>();

        outputMap.put("currentCO2e", currentCO2eSavingsTonnes);
        outputMap.put("goalCO2e", goalCO2eSavingsTonnes);

        return outputMap;
    }

    public float getGoalCO2eSavings() {
        return goalCO2eSavingsTonnes;
    }

    public void setGoalCO2eSavings(float savings) {
        goalCO2eSavingsTonnes = savings;
    }

    public float getCurrentCO2eSavings() {
        return currentCO2eSavingsTonnes;
    }

    public void addCO2eSavings(float addedSavingsInTonnes) {
        currentCO2eSavingsTonnes += addedSavingsInTonnes;
    }

    public float getCompletionPercentage() {
        return (float) Math.round(100 * currentCO2eSavingsTonnes / goalCO2eSavingsTonnes);
    }
}
