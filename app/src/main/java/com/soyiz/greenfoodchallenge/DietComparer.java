package com.soyiz.greenfoodchallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Compares CO2e levels in two diets and calculates the % of CO2e saved with new diet
 * Calculates the difference in kg of CO2e between diets
 * Calculates the CO2e saved if all of Vancouver made similar changes
 * Calculates the CO2e saved in litres of gasoline burned
 * All rounded to 0 decimal places*/
public class DietComparer {

    public DietComparer() {
    }

    //Subtracts new diet from old diet CO2e and returns the % difference of CO2e with new diet
    public float compareCO2ePercent(Diet oldDiet, Diet newDiet) {
        float CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        float percentSaved = (float) Math.round(100 * CO2eDifference / oldDiet.getYearlyCO2e());
        return percentSaved;
    }

    //Subtracts new diet from old diet CO2e and returns the difference in kg of CO2e with new diet
    public float compareCO2eKilos(Diet oldDiet, Diet newDiet) {
        float CO2eSaved = (float) Math.round(oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e());
        return CO2eSaved;
    }

    //Returns CO2e saved per year in millions of kg if everyone in Vancouver made the same changes
    public float getCO2eSavedInVancouver(Diet oldDiet, Diet newDiet) {
        float CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        //22.167 million non-vegetarians in Vancouver
        float CO2eSavedInVancouver = (float) Math.round(CO2eDifference * 22.167f);
        return CO2eSavedInVancouver;
    }


    //Returns the amount of gasoline needed to create an equivalent amount of CO2 per year
    public float getEquivalentLitresOfGasoline(Diet oldDiet, Diet newDiet) {
        float CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        //2.31 kg of CO2 per L of gasoline burned
        float litresOfGasoline = (float) Math.round(CO2eDifference / 2.31f);
        return litresOfGasoline;
    }

    // Compares the diets and produces a report.
    public String getChangeReport(Diet oldDiet, Diet newDiet) {

        String report = "Changes:\n" + (newDiet.getProteinPercent(ProteinSource.Beef) - oldDiet.getProteinPercent(ProteinSource.Beef)) + "% Beef; " +
                (newDiet.getProteinPercent(ProteinSource.Pork) - oldDiet.getProteinPercent(ProteinSource.Pork)) + "% Pork; " +
                (newDiet.getProteinPercent(ProteinSource.Chicken) - oldDiet.getProteinPercent(ProteinSource.Chicken)) + "% Chicken;\n" +
                (newDiet.getProteinPercent(ProteinSource.Fish) - oldDiet.getProteinPercent(ProteinSource.Fish)) + "% Fish; " +
                (newDiet.getProteinPercent(ProteinSource.Eggs) - oldDiet.getProteinPercent(ProteinSource.Eggs)) + "% Egg; " +
                (newDiet.getProteinPercent(ProteinSource.Beans) - oldDiet.getProteinPercent(ProteinSource.Beans)) + "%Beans; \n" +
                (newDiet.getProteinPercent(ProteinSource.Vegetables) - oldDiet.getProteinPercent(ProteinSource.Vegetables)) + " %Vegetables;\n" +
                "You will save " + this.compareCO2ePercent(oldDiet, newDiet) + "% CO2e.";
        if (this.compareCO2ePercent(oldDiet, newDiet) == 0) {
            return "Original diet is good enough!";
        }
        return report;
    }

    // Compares current C02e in diet and returns how it compares to regional average
    public static String getHowWellUserComparesToRegion
            (float currentC02e, float averageC02eInDietForArea) {
        List<String> howWellDietC02eCompares = new ArrayList<>(Arrays.asList(
                "This diet much produces less C02e than the",
                "This diet produces less C02e than the",
                "This diet produces about the same C02e as the",
                "This diet produces more C02e than the",
                "This diet produces much more C02e than the"
        ));
        if (currentC02e <= averageC02eInDietForArea * 0.75) {
            return howWellDietC02eCompares.get(0);
        } else if (currentC02e <= averageC02eInDietForArea * 0.9) {
            return howWellDietC02eCompares.get(1);
        } else if (currentC02e <= averageC02eInDietForArea * 1.1) {
            return howWellDietC02eCompares.get(2);
        } else if (currentC02e <= averageC02eInDietForArea * 1.25) {
            return howWellDietC02eCompares.get(3);
        } else {
            return howWellDietC02eCompares.get(4);
        }
    }
}