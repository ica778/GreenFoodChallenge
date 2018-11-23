package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProteinSourceTest {

    @Test
    public void getDailyServingTest() {
        assertEquals(187.5, ProteinSource.getDailyServing(), 1.0);
    }
}