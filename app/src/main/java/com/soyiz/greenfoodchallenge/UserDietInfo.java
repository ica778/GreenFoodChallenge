package com.soyiz.greenfoodchallenge;

import java.util.HashMap;
import java.util.Map;

public class UserDietInfo {
    private static final UserDietInfo ourInstance = new UserDietInfo();
    private Map<String, Integer> amountOfProteinMap;

    private UserDietInfo() {
        amountOfProteinMap = new HashMap<>();
    }

    public static UserDietInfo getInstance() {
        return ourInstance;
    }

    // Sets the amount in grams of a given protein
    public void setAmountOfProteinGrams(String typeOfProtein, Integer amountOfProtein) {
        amountOfProteinMap.put(typeOfProtein, amountOfProtein);
    }

    // Gets the amount in grams of a given protein
    public int getAmountOfProteinGrams(String typeOfProtein) {
        return amountOfProteinMap.get(typeOfProtein);
    }
    // Returns amount in KG of a given protein
    public float getAmountOfProteinKG(String typeOfProtein) {
        return amountOfProteinMap.get(typeOfProtein) / 1000;
    }

    // Returns sum of all proteins in grams
    public int getTotalAmountOfProteinGrams() {
        int sumOfAllProteinGrams = 0;
        for (int amountOfCurrentProteinGrams : amountOfProteinMap.values()) {
            sumOfAllProteinGrams += amountOfCurrentProteinGrams;
        }
        return sumOfAllProteinGrams;
    }

    // Returns sum of all proteins in KG
    public float getTotalAmountOfProteinKG() {
        int sumOfAllProteinGrams = 0;
        for (int amountOfCurrentProteinGrams : amountOfProteinMap.values()) {
            sumOfAllProteinGrams += amountOfCurrentProteinGrams;
        }
        return sumOfAllProteinGrams / 1000;
    }
}