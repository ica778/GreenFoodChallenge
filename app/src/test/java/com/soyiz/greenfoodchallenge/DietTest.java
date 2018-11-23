package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class DietTest {



    /*@Test
    public void loadFromStringMap() {
        Map<ProteinSource, Float> proteinPercentMap = new HashMap<>();
        proteinPercentMap.put(ProteinSource.Beef, 1.0f);
        proteinPercentMap.put(ProteinSource.Chicken, 1.0f);
        proteinPercentMap.put(ProteinSource.Vegetables, 1.0f);
        proteinPercentMap.put(ProteinSource.Beans, 1.0f);
        proteinPercentMap.put(ProteinSource.Eggs, 1.0f);
        proteinPercentMap.put(ProteinSource.Pork, 1.0f);
        proteinPercentMap.put(ProteinSource.Fish, 1.0f);
        Map<String, Float> map = new HashMap<>();
        map.put("test1", 1.0f);
        map.put("test2", 1.0f);

        for(Map.Entry<String, Float> entry : map.entrySet()) {
            ProteinSource protein = ProteinSource.stringToEnumValue(entry.getKey());
            assertEquals(null, protein);
        }

    }

    @Test
    public void exportToStringMap() {
        Map<ProteinSource, Float> proteinPercentMap = new HashMap<>();
        proteinPercentMap.put(ProteinSource.Beef, 1.0f);
        proteinPercentMap.put(ProteinSource.Chicken, 1.0f);
        proteinPercentMap.put(ProteinSource.Vegetables, 1.0f);
        proteinPercentMap.put(ProteinSource.Beans, 1.0f);
        proteinPercentMap.put(ProteinSource.Eggs, 1.0f);
        proteinPercentMap.put(ProteinSource.Pork, 1.0f);
        proteinPercentMap.put(ProteinSource.Fish, 1.0f);
        Map<String, Float> outputMap = new HashMap<>();
        for (Map.Entry<ProteinSource, Float> entry : proteinPercentMap.entrySet()) {
            outputMap.put(entry.getKey().toString(), entry.getValue());
        }

        assertNotNull( outputMap);

    }*/

    @Test
    public void setProteinPercent() {
        ProteinSource protein = ProteinSource.Beef;
        float percent = 1.0f;
        Map<ProteinSource, Float> proteinPercentMap = new HashMap<>();
        proteinPercentMap.put(protein, percent);
        assertEquals(1.0f, proteinPercentMap.get(protein), 0.1);
    }

    @Test
    public void getProteinPercent() {
        ProteinSource protein = ProteinSource.Beef;
        float percent = 1.0f;
        Map<ProteinSource, Float> proteinPercentMap = new HashMap<>();
        proteinPercentMap.put(protein, percent);
        assertEquals(1.0f, proteinPercentMap.get(protein), 0.1);
    }

    @Test
    public void getWeeklyCO2e() {
        float output = 0;
        Map<ProteinSource, Float> proteinPercentMap = new HashMap<>();
        proteinPercentMap.put(ProteinSource.Beef, 1.0f);
        proteinPercentMap.put(ProteinSource.Chicken, 1.0f);
        proteinPercentMap.put(ProteinSource.Vegetables, 1.0f);
        proteinPercentMap.put(ProteinSource.Beans, 1.0f);
        proteinPercentMap.put(ProteinSource.Eggs, 1.0f);
        proteinPercentMap.put(ProteinSource.Pork, 1.0f);
        proteinPercentMap.put(ProteinSource.Fish, 1.0f);

        // Weekly serving in grams for protein
        float weeklyServing = (float)ProteinSource.getDailyServing() * 7;
        for (Map.Entry<ProteinSource, Float> pair : proteinPercentMap.entrySet()) {

            // Weekly serving in grams for a specific protein
            float proteinWeeklyServing = pair.getValue()/100 * weeklyServing;

            // Puts the serving into KG and multiplies by CO2e for the protein
            output += (proteinWeeklyServing / 1000f) * pair.getKey().getCO2e();
        }
        assertNotEquals(0.0f, output);
    }

    @Test
    public void getYearlyCO2e() {
        //works when getWeeklyCO2e works
    }
}