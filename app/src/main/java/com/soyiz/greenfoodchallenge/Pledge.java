package com.soyiz.greenfoodchallenge;

public class Pledge {

    private float goalCO2eSavingsTonnes = -1f;
    private float currentCO2eSavingsTonnes = -1f;

    public Pledge(){}

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

    public void getCompletionPercentage() {
        float completionPercentage = (float)Math.round(100*currentCO2eSavingsTonnes/goalCO2eSavingsTonnes);
    }
}
