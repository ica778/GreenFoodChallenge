package com.soyiz.greenfoodchallenge;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class DietsCreatorTest {

    private Diet newUser = new Diet();
    private Diet user = new Diet();

    public void setUser() {
        user.setProteinPercent(ProteinSource.Beef, 35);
        user.setProteinPercent(ProteinSource.Pork, 10);
        user.setProteinPercent(ProteinSource.Chicken, 35);
        user.setProteinPercent(ProteinSource.Fish, 10);
        user.setProteinPercent(ProteinSource.Eggs, 5);
        user.setProteinPercent(ProteinSource.Beans, 0);
        user.setProteinPercent(ProteinSource.Vegetables, 5);
    }

    public void setNewuser() {
        newUser.setProteinPercent(ProteinSource.Beef, 100);
        newUser.setProteinPercent(ProteinSource.Pork, 0);
        newUser.setProteinPercent(ProteinSource.Chicken, 0);
        newUser.setProteinPercent(ProteinSource.Fish, 0);
        newUser.setProteinPercent(ProteinSource.Eggs, 0);
        newUser.setProteinPercent(ProteinSource.Beans, 0);
        newUser.setProteinPercent(ProteinSource.Vegetables, 0);
    }

    @Test
    public void testSetUserDiet() {
        setUser();
        DietsCreator creator = new DietsCreator();
        creator.setUserDiet(user);
        setNewuser();
        creator.setUserDiet(newUser);
        assertEquals(newUser.getProteinPercent(ProteinSource.Beef),
                creator.getUserDiet().getProteinPercent(ProteinSource.Beef), 0);
        assertEquals(newUser.getProteinPercent(ProteinSource.Pork),
                creator.getUserDiet().getProteinPercent(ProteinSource.Pork), 0);
        assertEquals(newUser.getProteinPercent(ProteinSource.Chicken),
                creator.getUserDiet().getProteinPercent(ProteinSource.Chicken), 0);
        assertEquals(newUser.getProteinPercent(ProteinSource.Fish),
                creator.getUserDiet().getProteinPercent(ProteinSource.Fish), 0);
        assertEquals(newUser.getProteinPercent(ProteinSource.Eggs),
                creator.getUserDiet().getProteinPercent(ProteinSource.Eggs), 0);
        assertEquals(newUser.getProteinPercent(ProteinSource.Beans),
                creator.getUserDiet().getProteinPercent(ProteinSource.Beans), 0);
        assertEquals(newUser.getProteinPercent(ProteinSource.Vegetables),
                creator.getUserDiet().getProteinPercent(ProteinSource.Vegetables
                ), 0);
    }

    @Test
    public void testCreateMeatEaterDiet() {
        setUser();
        DietsCreator creator = new DietsCreator();
        creator.setUserDiet(user);
        Diet meatEaterDiet = creator.createMeatEaterDiet();

        // Assert yearly CO2e decreases assuming starting diet with more CO2e
        assertTrue(user.getYearlyCO2e() > meatEaterDiet.getYearlyCO2e());
        float meatSum = (float)(meatEaterDiet.getProteinPercent(ProteinSource.Beef) +
                meatEaterDiet.getProteinPercent(ProteinSource.Pork) +
                meatEaterDiet.getProteinPercent(ProteinSource.Chicken) +
                meatEaterDiet.getProteinPercent(ProteinSource.Fish));
        float userSum = (float)(user.getProteinPercent(ProteinSource.Beef) +
                user.getProteinPercent(ProteinSource.Pork) +
                user.getProteinPercent(ProteinSource.Chicken) +
                user.getProteinPercent(ProteinSource.Fish));

        // Assert no change in total meat proportion
        assertTrue(meatSum == userSum);
        float totalSum = (float)(meatEaterDiet.getProteinPercent(ProteinSource.Eggs) +
                meatEaterDiet.getProteinPercent(ProteinSource.Beans) +
                meatEaterDiet.getProteinPercent(ProteinSource.Vegetables) + meatSum);

        // Assert proportions sum to 100%
        assertEquals(100f, totalSum, 0);
    }

    @Test
    public void testCreateLowMeatDiet() {
        setUser();
        DietsCreator creator = new DietsCreator();
        creator.setUserDiet(user);
        Diet lowMeatDiet = creator.createLowMeatDiet();

        // Assert yearly CO2e decreases
        assertTrue(user.getYearlyCO2e() > lowMeatDiet.getYearlyCO2e());
        float meatSum = (float)(lowMeatDiet.getProteinPercent(ProteinSource.Beef) +
                lowMeatDiet.getProteinPercent(ProteinSource.Pork) +
                lowMeatDiet.getProteinPercent(ProteinSource.Chicken) +
                lowMeatDiet.getProteinPercent(ProteinSource.Fish));
        float userSum = (float)(user.getProteinPercent(ProteinSource.Beef) +
                user.getProteinPercent(ProteinSource.Pork) +
                user.getProteinPercent(ProteinSource.Chicken) +
                user.getProteinPercent(ProteinSource.Fish));

        // Assert total proportion of meat decreases assuming starting diet with more CO2e
        assertTrue(userSum > meatSum);
        float totalSum = (float)(lowMeatDiet.getProteinPercent(ProteinSource.Eggs) +
                lowMeatDiet.getProteinPercent(ProteinSource.Beans) +
                lowMeatDiet.getProteinPercent(ProteinSource.Vegetables) + meatSum);
        // Assert proportions add to 100%
        assertEquals(100f, totalSum, 0);
    }

    @Test
    public void testCreateOnlyFishDiet() {
        setUser();
        DietsCreator creator = new DietsCreator();
        creator.setUserDiet(user);
        Diet onlyFishDiet = creator.createOnlyFishDiet();

        // Assert yearly CO2e decreases assuming starting diet with more CO2e
        assertTrue(user.getYearlyCO2e() > onlyFishDiet.getYearlyCO2e());
        float nonFishMeatSum = (float)(onlyFishDiet.getProteinPercent(ProteinSource.Beef) +
                onlyFishDiet.getProteinPercent(ProteinSource.Pork) +
                onlyFishDiet.getProteinPercent(ProteinSource.Chicken));

        // Assert fish is only meat that can have nonzero proportion
        assertEquals(0f, nonFishMeatSum, 0);
        float totalSum = (float)(onlyFishDiet.getProteinPercent(ProteinSource.Fish) +
                onlyFishDiet.getProteinPercent(ProteinSource.Eggs) +
                onlyFishDiet.getProteinPercent(ProteinSource.Beans) +
                onlyFishDiet.getProteinPercent(ProteinSource.Vegetables) + nonFishMeatSum);

        // Assert total sum of proportions is 100%
        assertEquals(100f, totalSum, 0);
    }

    @Test
    public void testCreateVegetarianDiet() {
        setUser();
        DietsCreator creator = new DietsCreator();
        creator.setUserDiet(user);
        Diet vegetarianDiet = creator.createVegetarianDiet();

        // Assert yearly CO2e decreases assuming starting diet with more CO2e
        assertTrue(user.getYearlyCO2e() > vegetarianDiet.getYearlyCO2e());
        float meatSum = (float)(vegetarianDiet.getProteinPercent(ProteinSource.Beef) +
                vegetarianDiet.getProteinPercent(ProteinSource.Pork) +
                vegetarianDiet.getProteinPercent(ProteinSource.Chicken) +
                vegetarianDiet.getProteinPercent(ProteinSource.Fish));

        // Assert meat proportions is 0%
        assertEquals(0f, meatSum, 0);
        float totalSum = (float)(vegetarianDiet.getProteinPercent(ProteinSource.Eggs) +
                vegetarianDiet.getProteinPercent(ProteinSource.Beans) +
                vegetarianDiet.getProteinPercent(ProteinSource.Vegetables) + meatSum);

        // Assert total proportions equals 100%
        assertEquals(100f, totalSum, 0);
    }

    @Test
    public void testCreateVeganDiet() {
        setUser();
        DietsCreator creator = new DietsCreator();
        creator.setUserDiet(user);
        Diet veganDiet = creator.createVeganDiet();

        // Assert yearly CO2e decreases assuming starting diet with more CO2e
        assertTrue(user.getYearlyCO2e() >= veganDiet.getYearlyCO2e());
        float meatEggSum = (float)(veganDiet.getProteinPercent(ProteinSource.Beef) +
                veganDiet.getProteinPercent(ProteinSource.Pork) +
                veganDiet.getProteinPercent(ProteinSource.Chicken) +
                veganDiet.getProteinPercent(ProteinSource.Fish) +
                veganDiet.getProteinPercent(ProteinSource.Eggs));

        // Assert non beans and vegetable sums are 0% of proportions
        assertEquals(0f, meatEggSum, 0);
        float totalSum = (float)(veganDiet.getProteinPercent(ProteinSource.Beans) +
                veganDiet.getProteinPercent(ProteinSource.Vegetables) + meatEggSum);

        // Assert total proportions sum to 100%
        assertEquals(100f, totalSum, 0);
    }
}