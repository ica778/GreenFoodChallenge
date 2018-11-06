package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserDietInfoTest {

    @Test
    public void getInstanceTest() {
    }

    @Test
    public void setAmountOfProteinGramsTest() {
        UserDietInfo.getInstance().setAmountOfProteinGrams("test", 10);
        assertEquals(10, UserDietInfo.getInstance().getAmountOfProteinGrams("test"));
    }

    @Test
    public void getAmountOfProteinGramsTest() {
        UserDietInfo.getInstance().setAmountOfProteinGrams("test", 10);
        assertEquals(10, UserDietInfo.getInstance().getAmountOfProteinGrams("test"));
    }
}