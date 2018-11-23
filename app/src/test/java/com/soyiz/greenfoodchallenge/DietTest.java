package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class DietTest {

    @Test
    public void setProteinPercent() {
        Diet diet = new Diet();
        double number = 10;
        diet.setProteinPercent(ProteinSource.Beans, number);
        assertEquals(number,diet.getProteinPercent(ProteinSource.Beans));
    }

    @Test
    public void getYearlyCO2e() {
        Diet diet = new Diet();
        diet.setProteinPercent(ProteinSource.Beans, 100);
        double weeklyServing = 187.5*7;
        double proteinWeeklyServing = 100 / 100 * weeklyServing;
        double output = (proteinWeeklyServing / 1000.0) * 2.0;
        assertEquals(output*52,diet.getYearlyCO2e());
    }

}