package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class DietTest {

    @Test
    public void setProteinPercentTest() {
        ProteinSource protein = ProteinSource.Beef;
        float percent = 1.0f;
        Map<ProteinSource, Float> proteinPercentMap = new HashMap<>();
        proteinPercentMap.put(protein, percent);
        assertEquals(1.0f, proteinPercentMap.get(protein), 0.1);

        Diet dietTest = new Diet();
        dietTest.setProteinPercent(ProteinSource.Beef, 10);
        dietTest.setProteinPercent(ProteinSource.Pork, 10);
        dietTest.setProteinPercent(ProteinSource.Fish, 10);
        dietTest.setProteinPercent(ProteinSource.Chicken, 10);
        dietTest.setProteinPercent(ProteinSource.Vegetables, 10);
        dietTest.setProteinPercent(ProteinSource.Beans, 10);
        dietTest.setProteinPercent(ProteinSource.Eggs, 40);

        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Beef), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Pork), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Fish), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Chicken), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Vegetables), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Beans), 0.1);
        assertEquals(40.0, dietTest.getProteinPercent(ProteinSource.Eggs), 0.1);
    }

    @Test
    public void getProteinPercentTest() {
        ProteinSource protein = ProteinSource.Beef;
        float percent = 1.0f;
        Map<ProteinSource, Float> proteinPercentMap = new HashMap<>();
        proteinPercentMap.put(protein, percent);
        assertEquals(1.0f, proteinPercentMap.get(protein), 0.1);

        Diet dietTest = new Diet();
        dietTest.setProteinPercent(ProteinSource.Beef, 10);
        dietTest.setProteinPercent(ProteinSource.Pork, 10);
        dietTest.setProteinPercent(ProteinSource.Fish, 10);
        dietTest.setProteinPercent(ProteinSource.Chicken, 10);
        dietTest.setProteinPercent(ProteinSource.Vegetables, 10);
        dietTest.setProteinPercent(ProteinSource.Beans, 10);
        dietTest.setProteinPercent(ProteinSource.Eggs, 40);

        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Beef), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Pork), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Fish), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Chicken), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Vegetables), 0.1);
        assertEquals(10.0, dietTest.getProteinPercent(ProteinSource.Beans), 0.1);
        assertEquals(40.0, dietTest.getProteinPercent(ProteinSource.Eggs), 0.1);
    }

    @Test
    public void getWeeklyCO2eTest() {
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
        float weeklyServing = (float) ProteinSource.getDailyServing() * 7;
        for (Map.Entry<ProteinSource, Float> pair : proteinPercentMap.entrySet()) {

            // Weekly serving in grams for a specific protein
            float proteinWeeklyServing = pair.getValue() / 100 * weeklyServing;

            // Puts the serving into KG and multiplies by CO2e for the protein
            output += (proteinWeeklyServing / 1000f) * pair.getKey().getCO2e();
        }
        assertNotEquals(0.0f, output);

        Diet dietTest = new Diet();
        dietTest.setProteinPercent(ProteinSource.Beef, 10);
        dietTest.setProteinPercent(ProteinSource.Pork, 10);
        dietTest.setProteinPercent(ProteinSource.Fish, 10);
        dietTest.setProteinPercent(ProteinSource.Chicken, 10);
        dietTest.setProteinPercent(ProteinSource.Vegetables, 10);
        dietTest.setProteinPercent(ProteinSource.Beans, 10);
        dietTest.setProteinPercent(ProteinSource.Eggs, 40);

        assertEquals(10.0, dietTest.getWeeklyCO2e(), 1.0);
    }

    @Test
    public void getYearlyCO2eTest() {
        Diet dietTest = new Diet();
        dietTest.setProteinPercent(ProteinSource.Beef, 10);
        dietTest.setProteinPercent(ProteinSource.Pork, 10);
        dietTest.setProteinPercent(ProteinSource.Fish, 10);
        dietTest.setProteinPercent(ProteinSource.Chicken, 10);
        dietTest.setProteinPercent(ProteinSource.Vegetables, 10);
        dietTest.setProteinPercent(ProteinSource.Beans, 10);
        dietTest.setProteinPercent(ProteinSource.Eggs, 40);

        assertEquals(514.0, dietTest.getYearlyCO2e(), 1.0);
    }
}