package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class MealCardTest {

    @Test
    public void testSetUuid() {
        String string = "string";
        MealCard mealCard = new MealCard();
        String stringOrig = mealCard.getUuid();
        assertNotEquals(string, stringOrig);
        mealCard.setUuid(string);
        String stringNew = mealCard.getUuid();
        assertEquals(string, stringNew);
    }

    @Test
    public void testSetMealName() {
        String string = "string";
        MealCard mealCard = new MealCard();
        String stringOrig = mealCard.getMealName();
        assertNotEquals(string, stringOrig);
        mealCard.setMealName(string);
        String stringNew = mealCard.getMealName();
        assertEquals(string, stringNew);
    }

    @Test
    public void testSetMealProtein() {
        String string = "string";
        MealCard mealCard = new MealCard();
        String stringOrig = mealCard.getMealProtein();
        assertNotEquals(string, stringOrig);
        mealCard.setMealProtein(string);
        String stringNew = mealCard.getMealProtein();
        assertEquals(string, stringNew);
    }

    @Test
    public void testSetMealDescription() {
        String string = "string";
        MealCard mealCard = new MealCard();
        String stringOrig = mealCard.getMealDescription();
        assertNotEquals(string, stringOrig);
        mealCard.setMealDescription(string);
        String stringNew = mealCard.getMealDescription();
        assertEquals(string, stringNew);
    }

    @Test
    public void testSetRestaurantName() {
        String string = "string";
        MealCard mealCard = new MealCard();
        String stringOrig = mealCard.getRestaurantName();
        assertNotEquals(string, stringOrig);
        mealCard.setRestaurantName(string);
        String stringNew = mealCard.getRestaurantName();
        assertEquals(string, stringNew);
    }

    @Test
    public void testSetRestaurantLocation() {
        String string = "string";
        MealCard mealCard = new MealCard();
        String stringOrig = mealCard.getRestaurantLocation();
        assertNotEquals(string, stringOrig);
        mealCard.setRestaurantLocation(string);
        String stringNew = mealCard.getRestaurantLocation();
        assertEquals(string, stringNew);
    }

    @Test
    public void testSetCreator() {
        String string = "string";
        MealCard mealCard = new MealCard();
        String stringOrig = mealCard.getCreator();
        assertNotEquals(string, stringOrig);
        mealCard.setCreator(string);
        String stringNew = mealCard.getCreator();
        assertEquals(string, stringNew);
    }
}