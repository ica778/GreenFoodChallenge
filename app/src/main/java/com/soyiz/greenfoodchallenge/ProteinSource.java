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
    private static final Map<ProteinSource, Float> proteinC02eMap;

    static {
        proteinC02eMap = new HashMap<>();
        proteinC02eMap.put(ProteinSource.Beef, 27f);
        proteinC02eMap.put(ProteinSource.Pork, 12.1f);
        proteinC02eMap.put(ProteinSource.Chicken, 6.9f);
        proteinC02eMap.put(ProteinSource.Fish, 6.1f);
        proteinC02eMap.put(ProteinSource.Eggs, 4.8f);
        proteinC02eMap.put(ProteinSource.Beans, 2f);
        proteinC02eMap.put(ProteinSource.Vegetables, 2f);
    }

    // Converts a string input to the enum equivalent
    // Can't use a switch statement because they only work on constant expressions :(
    public static ProteinSource stringToEnumValue(String input)
    {
        if (input.equals(Beef.toString()))
        {
            return Beef;
        }
        else if (input.equals(Pork.toString()))
        {
            return Pork;
        }
        else if (input.equals(Chicken.toString()))
        {
            return Chicken;
        }
        else if (input.equals(Fish.toString()))
        {
            return Fish;
        }
        else if (input.equals(Eggs.toString()))
        {
            return Eggs;
        }
        else if (input.equals(Beans.toString()))
        {
            return Beans;
        }
        else if (input.equals(Vegetables.toString()))
        {
            return Vegetables;
        }
        else
        {
            // Literally can't happen, unless we give it bad input
            return null;
        }
    }

    public float getCO2e() {
        return proteinC02eMap.get(this);
    }

    public static float getDailyServing() {
        return 187.5f;
    }
}
