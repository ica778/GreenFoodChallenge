package com.soyiz.greenfoodchallenge;

import java.util.HashMap;
import java.util.Map;

public class UserDietInfo {
    private static final UserDietInfo ourInstance = new UserDietInfo();

    public static UserDietInfo getInstance() {
        return ourInstance;
    }

    private Map<String, Integer> amountOfProteinMap;

    private UserDietInfo() {
        amountOfProteinMap = new HashMap<>();
    }

    // Sets the amount in grams of a given protein
    public void setAmountOfProteinGrams(String typeOfProtein, Integer amountOfProtein) {
        amountOfProteinMap.put(typeOfProtein, amountOfProtein);
    }

    // Gets the amount in grams of a given protein
    public int getAmountOfProteinGrams(String typeOfProtein) {
        return amountOfProteinMap.get(typeOfProtein);
    }
}