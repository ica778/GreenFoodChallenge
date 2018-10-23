package com.soyiz.greenfoodchallenge;

import org.junit.Test;
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
    public void testCompareCO2e() {
        DietComparer comparer = new DietComparer();
        setNewer();
        setOlder();
        float CO2e = comparer.compareCO2e(older,newer);
        //Assert CO2e saved is calculated correctly (older diet - newer diet)
        assertEquals(older.getYearlyCO2e()-newer.getYearlyCO2e(), CO2e, 0);
    }

    @Test
    public void testGetCO2eSavedInVancouver() {
        DietComparer comparer = new DietComparer();
        setNewer();
        setOlder();
        float CO2e = comparer.compareCO2e(older,newer);
        comparer.setCO2eSaved(CO2e);
        float CO2eVancouver = comparer.getCO2eSavedInVancouver();
        /*Assert CO2e saved in Vancouver is correct (~22.167 million non vegetarians
        * who can change to new diet)*/
        assertEquals(CO2e*22.167f, CO2eVancouver, 0);
    }

    @Test
    public void testGetEquivalentLitresOfGasoline() {
        DietComparer comparer = new DietComparer();
        setNewer();
        setOlder();
        float CO2e = comparer.compareCO2e(older,newer);
        comparer.setCO2eSaved(CO2e);
        float litresOfGasoline = comparer.getEquivalentLitresOfGasoline();
        //Assert proper amount of equivalent gas (2.31 kg of CO2 per L of gas burned)
        assertEquals(CO2e/2.31f, litresOfGasoline, 0);
    }

}