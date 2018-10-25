package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DietComparerTest {

    private Diet older = new Diet();
    private Diet newer = new Diet();

    public void setOlder() {
        older.setProteinPercent(ProteinSource.Beef,35);
        older.setProteinPercent(ProteinSource.Pork, 10);
        older.setProteinPercent(ProteinSource.Chicken, 35);
        older.setProteinPercent(ProteinSource.Fish, 10);
        older.setProteinPercent(ProteinSource.Eggs, 5);
        older.setProteinPercent(ProteinSource.Beans, 0);
        older.setProteinPercent(ProteinSource.Vegetables, 5);
    }

    public void setNewer() {
        newer.setProteinPercent(ProteinSource.Beef, 10);
        newer.setProteinPercent(ProteinSource.Pork, 35);
        newer.setProteinPercent(ProteinSource.Chicken, 10);
        newer.setProteinPercent(ProteinSource.Fish, 35);
        newer.setProteinPercent(ProteinSource.Eggs, 0);
        newer.setProteinPercent(ProteinSource.Beans, 5);
        newer.setProteinPercent(ProteinSource.Vegetables, 5);
    }

    @Test
    public void testCompareCO2ePercent() {
        DietComparer comparer = new DietComparer();
        setNewer();
        setOlder();
        float CO2e = comparer.compareCO2ePercent(older,newer);
        float difference = older.getYearlyCO2e() - newer.getYearlyCO2e();
        float expected = (float)Math.round(100*difference/older.getYearlyCO2e());
        // Assert CO2e saved is calculated correctly ((older-newer)/older*100)
        assertEquals(expected, CO2e, 0);
    }

    @Test
    public void testCompareCO2eKilos() {
        DietComparer comparer = new DietComparer();
        setNewer();
        setOlder();
        float CO2e = comparer.compareCO2eKilos(older,newer);
        float difference = older.getYearlyCO2e() - newer.getYearlyCO2e();
        // Assert CO2e saved is calculated correctly (older diet - newer diet)
        assertEquals((float)Math.round(difference), CO2e, 0);
    }

    @Test
    public void testGetCO2eSavedInVancouver() {
        DietComparer comparer = new DietComparer();
        setNewer();
        setOlder();
        float CO2eVancouver = comparer.getCO2eSavedInVancouver(older, newer);
        float difference = older.getYearlyCO2e() - newer.getYearlyCO2e();
        float expected = (float)Math.round(22.167f*difference);
        /* Assert CO2e saved in Vancouver is correct (~22.167 million non vegetarians
         * who can change to new diet) */
        assertEquals(expected, CO2eVancouver, 0);
    }

    @Test
    public void testGetEquivalentLitresOfGasoline() {
        DietComparer comparer = new DietComparer();
        setNewer();
        setOlder();
        float litresOfGasoline = comparer.getEquivalentLitresOfGasoline(older, newer);
        float difference = older.getYearlyCO2e() - newer.getYearlyCO2e();
        float expected = (float)Math.round(difference/2.31f);
        // Assert proper amount of equivalent gas (2.31 kg of CO2 per L of gas burned)
        assertEquals(expected, litresOfGasoline, 0);
    }

    @Test
    public void testGetChangeReport() {
        DietComparer comparer = new DietComparer();
        Diet dietTestOne = new Diet();
        Diet dietTestTwo = new Diet();
        dietTestOne.setProteinPercent(ProteinSource.Beef,35);
        dietTestOne.setProteinPercent(ProteinSource.Pork, 10);
        dietTestOne.setProteinPercent(ProteinSource.Chicken, 35);
        dietTestOne.setProteinPercent(ProteinSource.Fish, 10);
        dietTestOne.setProteinPercent(ProteinSource.Eggs, 5);
        dietTestOne.setProteinPercent(ProteinSource.Beans, 0);
        dietTestOne.setProteinPercent(ProteinSource.Vegetables, 5);
        dietTestTwo.setProteinPercent(ProteinSource.Beef,35);
        dietTestTwo.setProteinPercent(ProteinSource.Pork, 10);
        dietTestTwo.setProteinPercent(ProteinSource.Chicken, 35);
        dietTestTwo.setProteinPercent(ProteinSource.Fish, 10);
        dietTestTwo.setProteinPercent(ProteinSource.Eggs, 5);
        dietTestTwo.setProteinPercent(ProteinSource.Beans, 0);
        dietTestTwo.setProteinPercent(ProteinSource.Vegetables, 5);
        assertEquals("Original diet is good enough!", comparer.getChangeReport(dietTestOne, dietTestTwo));
    }

    @Test
    public void testGetHowWellUserComparesToRegion() {
        List<String> howWellDietC02eCompares = new ArrayList<>(Arrays.asList(
                "This diet much produces less C02e than the",
                "This diet produces less C02e than the",
                "This diet produces about the same C02e as the",
                "This diet produces more C02e than the",
                "This diet produces much more C02e than the"
        ));
        DietComparer comparer = new DietComparer();
        float averageC02eInDietForArea = 1000f;
        int actualAnswer = 0;
        for (float amountOfC02eToTest = 0f;
             amountOfC02eToTest < averageC02eInDietForArea * 2;
             amountOfC02eToTest = amountOfC02eToTest + 0.1f) {
            if (amountOfC02eToTest <= averageC02eInDietForArea * 0.75) {
                actualAnswer = 0;
            } else if (amountOfC02eToTest <= averageC02eInDietForArea * 0.9) {
                actualAnswer = 1;
            } else if (amountOfC02eToTest <= averageC02eInDietForArea * 1.1) {
                actualAnswer = 2;
            } else if (amountOfC02eToTest <= averageC02eInDietForArea * 1.25) {
                actualAnswer = 3;
            } else {
                actualAnswer = 4;
            }
            assertEquals(DietComparer
                            .getHowWellUserComparesToRegion(amountOfC02eToTest, averageC02eInDietForArea)
                            , howWellDietC02eCompares.get(actualAnswer));
        }

    }
}