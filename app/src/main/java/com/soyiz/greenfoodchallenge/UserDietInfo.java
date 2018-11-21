package com.soyiz.greenfoodchallenge;

import java.util.HashMap;
import java.util.Map;

public class UserDietInfo {
    private static final UserDietInfo ourInstance = new UserDietInfo();
    private Map<String, Float> amountOfProteinMap;

    private UserDietInfo() {
        amountOfProteinMap = new HashMap<>();
    }

    public static UserDietInfo getInstance() {
        return ourInstance;
    }

    public Map getDietMap() {
        return amountOfProteinMap;
    }

    // Sets the amount in grams of a given protein
    public void setAmountOfProteinGrams(String typeOfProtein, float amountOfProtein) {
        amountOfProteinMap.put(typeOfProtein, amountOfProtein);
    }

    // Gets the amount in grams of a given protein
    public float getAmountOfProteinGrams(String typeOfProtein) {
        return amountOfProteinMap.get(typeOfProtein);
    }
    // Returns amount in KG of a given protein
    public float getAmountOfProteinKG(String typeOfProtein) {
        return amountOfProteinMap.get(typeOfProtein) / 1000;
    }

    // Returns sum of all proteins in grams THIS WILL ALWAYS BE WHOLE NUMBER
    public float getTotalAmountOfProteinGrams() {
        int sumOfAllProteinGrams = 0;
        for (float amountOfCurrentProteinGrams : amountOfProteinMap.values()) {
            sumOfAllProteinGrams += amountOfCurrentProteinGrams;
        }
        return sumOfAllProteinGrams;
    }

    // Returns sum of all proteins in KG
    public float getTotalAmountOfProteinKG() {
        int sumOfAllProteinGrams = 0;
        for (float amountOfCurrentProteinGrams : amountOfProteinMap.values()) {
            sumOfAllProteinGrams += amountOfCurrentProteinGrams;
        }
        return sumOfAllProteinGrams / 1000;
    }
}