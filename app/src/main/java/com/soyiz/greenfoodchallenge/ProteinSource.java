package com.soyiz.greenfoodchallenge;

import java.util.HashMap;
import java.util.Map;

public enum ProteinSource {
    Beef,
    Pork,
    Chicken,
    Fish,
    Eggs,
    Beans,
    Vegetables;
    public static final double BEEF_PROTEIN = 27.0;
    public static final double PORK_PROTEIN = 12.1;
    public static final double CHICKEN_PROTEIN = 6.9;
    public static final double FISH_PROTEIN = 6.1;
    public static final double EGGS_PROTEIN = 4.8;
    public static final double BEANS_PROTEIN = 2.0;
    public static final double VEGETABLES_PROTEIN = 2.0;
    public static final double DAILY_PROTEIN_SERVING = 187.5;
    private static final Map<ProteinSource, Double> proteinC02eMap;

    static {
        proteinC02eMap = new HashMap<>();
        proteinC02eMap.put(ProteinSource.Beef, BEEF_PROTEIN);
        proteinC02eMap.put(ProteinSource.Pork, PORK_PROTEIN);
        proteinC02eMap.put(ProteinSource.Chicken, CHICKEN_PROTEIN);
        proteinC02eMap.put(ProteinSource.Fish, FISH_PROTEIN);
        proteinC02eMap.put(ProteinSource.Eggs, EGGS_PROTEIN);
        proteinC02eMap.put(ProteinSource.Beans, BEANS_PROTEIN);
        proteinC02eMap.put(ProteinSource.Vegetables, VEGETABLES_PROTEIN);
    }

    // Converts a string input to the enum equivalent
    // Can't use a switch statement because they only work on constant expressions :(
    public static ProteinSource stringToEnumValue(String input) {
        if (input.equals(Beef.toString())) {
            return Beef;
        } else if (input.equals(Pork.toString())) {
            return Pork;
        } else if (input.equals(Chicken.toString())) {
            return Chicken;
        } else if (input.equals(Fish.toString())) {
            return Fish;
        } else if (input.equals(Eggs.toString())) {
            return Eggs;
        } else if (input.equals(Beans.toString())) {
            return Beans;
        } else if (input.equals(Vegetables.toString())) {
            return Vegetables;
        } else {
            // Literally can't happen, unless we give it bad input
            return null;
        }
    }

    public static double getDailyServing() {
        return DAILY_PROTEIN_SERVING;
    }

    // Gets c02e per gram
    public double getCO2e() {
        return proteinC02eMap.get(this);
    }
}
