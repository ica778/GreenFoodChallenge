package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class UserDietInfoTest {

    @Test
    public void getInstanceTest() {
    }

    @Test
    public void setAmountOfProteinGramsTest() {
        UserDietInfo.getInstance().setAmountOfProteinGrams("test", 10);
        assertEquals(10, UserDietInfo.getInstance().getAmountOfProteinGrams("test"));
        Map<String, Integer> amountOfProteinMap = new HashMap<>();
        amountOfProteinMap.put("test1", 10);
        Integer expected = 10;
        assertEquals(expected, amountOfProteinMap.get("test1"));

    }

    @Test
    public void getAmountOfProteinGramsTest() {
        UserDietInfo.getInstance().setAmountOfProteinGrams("test", 10);
        assertEquals(10, UserDietInfo.getInstance().getAmountOfProteinGrams("test"));
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

        assertEquals(60, sumOfAllProteinGrams);
    }
}