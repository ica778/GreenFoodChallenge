package com.soyiz.greenfoodchallenge;

/*Compares CO2e levels in two diets and calculates the % of CO2e saved with new diet
 * Calculates the difference in kg of CO2e between diets
 * Calculates the CO2e saved if all of Vancouver made similar changes
 * Calculates the CO2e saved in litres of gasoline burned
 * All rounded to 0 decimal places*/
public class DietComparer {

    public DietComparer() {}

    //Subtracts new diet from old diet CO2e and returns the % difference of CO2e with new diet
    public float compareCO2ePercent(Diet oldDiet, Diet newDiet) {
        float CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        float percentSaved = (float)Math.round(100*CO2eDifference/oldDiet.getYearlyCO2e());
        return percentSaved;
    }

    //Subtracts new diet from old diet CO2e and returns the difference in kg of CO2e with new diet
    public float compareCO2eKilos(Diet oldDiet, Diet newDiet) {
        float CO2eSaved = (float)Math.round(oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e());
        return CO2eSaved;
    }

    //Returns CO2e saved per year in millions of kg if everyone in Vancouver made the same changes
    public float getCO2eSavedInVancouver(Diet oldDiet, Diet newDiet) {
        float CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        //22.167 million non-vegetarians in Vancouver
        float CO2eSavedInVancouver = (float)Math.round(CO2eDifference*22.167f);
        return CO2eSavedInVancouver;
    }

    //Returns the amount of gasoline needed to create an equivalent amount of CO2 per year
    public float getEquivalentLitresOfGasoline(Diet oldDiet, Diet newDiet) {
        float CO2eDifference = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        //2.31 kg of CO2 per L of gasoline burned
        float litresOfGasoline = (float)Math.round(CO2eDifference/2.31f);
        return litresOfGasoline;
    }
}
