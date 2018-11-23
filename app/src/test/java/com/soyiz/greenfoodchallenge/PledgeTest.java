package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class PledgeTest {

    @Test
    public void testGetGoalCO2eSavingsTonnes() {
        float number = 134.6f;
        Pledge pledge = new Pledge();
        float numberOrig = pledge.getGoalCO2eSavings();
        assertNotEquals(number, numberOrig);
        pledge.setGoalCO2eSavings(number);
        float numberNew = pledge.getGoalCO2eSavings();
        assertEquals(number, numberNew, 0);
    }

    @Test
    public void testGetCurrentCO2eSavings() {
        float number = 134.6f;
        Pledge pledge = new Pledge();
        float numberOrig = pledge.getCurrentCO2eSavings();
        assertNotEquals(number, numberOrig);
        pledge.setCurrentCO2eSavings(number);
        float numberNew = pledge.getCurrentCO2eSavings();
        assertEquals(number, numberNew, 0);
    }

    @Test
    public void testAddCO2eSavings() {
        Pledge pledge = new Pledge();
        pledge.setCurrentCO2eSavings(0f);
        pledge.addCO2eSavings(100f);
        assertEquals(0f+100f,pledge.getCurrentCO2eSavings(), 0f);
    }

    @Test
    public void testGetCompletionPercentage() {
        Pledge pledge = new Pledge();
        pledge.setCurrentCO2eSavings(10f);
        pledge.setGoalCO2eSavings(5f);
        assertEquals(100 * pledge.getCurrentCO2eSavings() / pledge.getGoalCO2eSavings()
                ,pledge.getCompletionPercentage(), 0f);
    }

}