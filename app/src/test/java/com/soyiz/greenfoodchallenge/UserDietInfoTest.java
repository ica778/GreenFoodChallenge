package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class UserDietInfoTest {

    private Map<String, Integer> testMap = new HashMap<>();

    @Test
    public void getDietMapTest() {
        testMap.put("test1", 1);
        testMap.put("test2", 2);
        testMap.put("test3", 3);
        assertEquals(testMap, testMap);
        Map<String, Float> testGetDietMap = new HashMap<>();
        testGetDietMap.put("test", 10.0f);
        assertEquals(testGetDietMap, UserDietInfo.getInstance().getDietMap());
    }

    @Test
    public void getAmountOfProteinKGTest() {
        UserDietInfo.getInstance().setAmountOfProteinGrams("test", 10);
        assertEquals(10.0 / 1000f, UserDietInfo.getInstance().getAmountOfProteinKG("test"), 0.1);
    }

    @Test
    public void getTotalAmountOfProteinKGTest() {
        Map<String, Integer> amountOfProteinMap = new HashMap<>();
        amountOfProteinMap.put("test1", 10);
        amountOfProteinMap.put("test2", 10);
        amountOfProteinMap.put("test3", 10);
        amountOfProteinMap.put("test4", 10);
        amountOfProteinMap.put("test5", 10);
        amountOfProteinMap.put("test6", 10);
        int sumOfAllProteinKG = 0;
        assertEquals(0, sumOfAllProteinKG);

        for (int amountOfCurrentProteinKG : amountOfProteinMap.values()) {
            sumOfAllProteinKG += (int) amountOfCurrentProteinKG;
        }

        assertEquals(60, sumOfAllProteinKG);
    }

    @Test
    public void getInstanceTest() {
    }

    @Test
    public void setAmountOfProteinGramsTest() {
        UserDietInfo.getInstance().setAmountOfProteinGrams("test", 10);
        assertEquals(10.0, UserDietInfo.getInstance().getAmountOfProteinGrams("test"), 0.1);
        Map<String, Integer> amountOfProteinMap = new HashMap<>();
        amountOfProteinMap.put("test1", 10);
        Integer expected = 10;
        assertEquals(expected, amountOfProteinMap.get("test1"));
    }

    @Test
    public void getAmountOfProteinGramsTest() {
        UserDietInfo.getInstance().setAmountOfProteinGrams("test", 10);
        assertEquals(10.0, UserDietInfo.getInstance().getAmountOfProteinGrams("test"), 01);
    }

    @Test
    public void getTotalAmountOfProteinGramsTest() {
        Map<String, Integer> amountOfProteinMap = new HashMap<>();
        amountOfProteinMap.put("test1", 10);
        amountOfProteinMap.put("test2", 10);
        amountOfProteinMap.put("test3", 10);
        amountOfProteinMap.put("test4", 10);
        amountOfProteinMap.put("test5", 10);
        amountOfProteinMap.put("test6", 10);
        int sumOfAllProteinGrams = 0;
        assertEquals(0, sumOfAllProteinGrams);

        for (int amountOfCurrentProteinGrams : amountOfProteinMap.values()) {
            sumOfAllProteinGrams += (int) amountOfCurrentProteinGrams;
        }
        assertEquals(10.0, (int) UserDietInfo.getInstance().getTotalAmountOfProteinGrams(), 1);

        assertEquals(10.0 / 1000, (int) UserDietInfo.getInstance().getTotalAmountOfProteinKG(), 1);

        assertEquals(60, sumOfAllProteinGrams);
    }

}