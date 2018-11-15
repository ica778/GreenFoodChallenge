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

    DietComparer() {
    }

    // Returns the litres of gasoline equivalent to kg of C02e
    public static float getLitresOfGasolineEquivalentToDietC02e(float kgC02e) {
        // 1 L of gasoline produces approximately 2.3 kg of C02
        float litresOfGasoline = kgC02e / 2.3f;
        return litresOfGasoline;

    }

    // Compares current C02e in diet and returns how it compares to average
    public static String getHowWellC02eComparesToAverage
    (double kgC02e, double averageC02e) {
        List<String> howWellDietC02eCompares = new ArrayList<>(Arrays.asList(
                "much less C02e",
                "less C02e",
                "about the same C02e",
                "more C02e",
                "much more C02e"
        ));

        if (kgC02e <= averageC02e * 0.75) {
            return howWellDietC02eCompares.get(0);
        } else if (kgC02e <= averageC02e * 0.9) {
            return howWellDietC02eCompares.get(1);
        } else if (kgC02e <= averageC02e * 1.1) {
            return howWellDietC02eCompares.get(2);
        } else if (kgC02e <= averageC02e * 1.25) {
            return howWellDietC02eCompares.get(3);
        } else {
            return howWellDietC02eCompares.get(4);
        }
    }

    public static double getHowManyKGOfC02eAYear() {
        double totalTonnes = 365 * (27 * UserDietInfo.getInstance().getAmountOfProteinGrams("beef")
                + 12.1F * UserDietInfo.getInstance().getAmountOfProteinGrams("pork")
                + 6.9F * UserDietInfo.getInstance().getAmountOfProteinGrams("chicken")
                + 6.1F * UserDietInfo.getInstance().getAmountOfProteinGrams("fish")
                + 4.8F * UserDietInfo.getInstance().getAmountOfProteinGrams("egg")
                + 2 * UserDietInfo.getInstance().getAmountOfProteinGrams("bean")
                + 2 * UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable")) / 1000;
        return totalTonnes;
    }

    public static double getHowManyTonnesOfC02eAYear() {
        double totalTonnes = getHowManyKGOfC02eAYear();
        totalTonnes = totalTonnes / 1000;
        return totalTonnes;
    }

    //Subtracts new diet from old diet CO2e and returns the % difference of CO2e with new diet
    public double compareCO2ePercent(Diet oldDiet, Diet newDiet) {
        double CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        return (double) Math.round(100 * CO2eDifference / oldDiet.getYearlyCO2e());
    }

    //Subtracts new diet from old diet CO2e and returns the difference in kg of CO2e with new diet
    public double compareCO2eKilos(Diet oldDiet, Diet newDiet) {
        return (double) Math.round(oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e());
    }

    //Returns CO2e saved per year in millions of kg if everyone in Vancouver made the same changes
    public double getCO2eSavedInVancouver(Diet oldDiet, Diet newDiet) {
        double CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        //22.167 million non-vegetarians in Vancouver
        return (double) Math.round(CO2eDifference * 22.167f);
    }

    //Returns the amount of gasoline needed to create an equivalent amount of CO2 per year
    public double getEquivalentLitresOfGasoline(Diet oldDiet, Diet newDiet) {
        double CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        //2.31 kg of CO2 per L of gasoline burned
        return (double) Math.round(CO2eDifference / 2.31f);
    }

    // Compares the diets and produces a report.
    public String getChangeReport(Diet oldDiet, Diet newDiet) {
        String report = "Changes:\n\nBeef/Pork/Chicken/Fish/Egg/Beans/Vegetables\n" + (
                (double) Math.round(newDiet.getProteinPercent(ProteinSource.Beef) - oldDiet
                        .getProteinPercent(ProteinSource.Beef)) + "%/" +
                        (double) Math.round((newDiet.getProteinPercent(ProteinSource.Pork) - oldDiet
                                .getProteinPercent(ProteinSource.Pork))) + "%/" +
                        (double) Math.round((newDiet.getProteinPercent(ProteinSource.Chicken) - oldDiet
                                .getProteinPercent(ProteinSource.Chicken)))) + "%/" +
                (double) Math.round((newDiet.getProteinPercent(ProteinSource.Fish) - oldDiet
                        .getProteinPercent(ProteinSource.Fish))) + "%/" +
                (double) Math.round((newDiet.getProteinPercent(ProteinSource.Eggs) - oldDiet
                        .getProteinPercent(ProteinSource.Eggs))) + "%/" +
                (double) Math.round((newDiet.getProteinPercent(ProteinSource.Beans) - oldDiet
                        .getProteinPercent(ProteinSource.Beans))) + "%/" +
                (double) Math.round((newDiet.getProteinPercent(ProteinSource.Vegetables) - oldDiet
                        .getProteinPercent(ProteinSource.Vegetables))) + "%\n\n" +
                "You will save " + this.compareCO2ePercent(oldDiet, newDiet)
                + "% CO2e which is about " +
                this.compareCO2eKilos(oldDiet, newDiet) + "kg per year" + "(CO2 of  "
                + getEquivalentLitresOfGasoline(oldDiet, newDiet) + "L gas)."
                + " If everyone in Vancouver made the same changes, "
                + "the city will save " + getCO2eSavedInVancouver(oldDiet, newDiet) + " millions kg CO2e.";
        if (this.compareCO2ePercent(oldDiet, newDiet) == 0) {
            return "Original diet is good enough!";
        }
        return report;
    }
}