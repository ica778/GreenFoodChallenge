package com.soyiz.greenfoodchallenge;

import java.util.HashMap;
import java.util.Map;

public enum ProteinSource
{
    Beef,
    Pork,
    Chicken,
    Fish,
    Eggs,
    Beans,
    Vegetables;

    private static final Map<ProteinSource, Float> proteinC02eMap;
    static
    {
        proteinC02eMap = new HashMap<>();
        proteinC02eMap.put(ProteinSource.Beef, 27f);
        proteinC02eMap.put(ProteinSource.Pork, 12.1f);
        proteinC02eMap.put(ProteinSource.Chicken, 6.9f);
        proteinC02eMap.put(ProteinSource.Fish, 6.1f);
        proteinC02eMap.put(ProteinSource.Eggs, 4.8f);
        proteinC02eMap.put(ProteinSource.Beans, 2f);
        proteinC02eMap.put(ProteinSource.Vegetables, 2f);
    }

    public float getCO2e()
    {
        return proteinC02eMap.get(this);
    }

    public static float getDailyServing()
    {
        return 187.5f;
    }
}
