package com.soyiz.greenfoodchallenge;

/*Compares CO2e levels in two diets and calculates the difference,
 * Calculates the CO2e saved if all of Vancouver made similar changes
 * Calculates the CO2e saved in litres of gasoline burned*/
public class DietComparer {

    private float CO2eSaved = 0;

    public DietComparer() {}

    //Subtracts new diet from old diet CO2e and returns the CO2e saved with new diet
    public float compareCO2e(Diet oldDiet, Diet newDiet) {
        CO2eSaved = oldDiet.getYearlyCO2e() - newDiet.getYearlyCO2e();
        return CO2eSaved;
    }

    //Returns CO2e saved in millions of kg if everyone in Vancouver made the same changes
    public float getCO2eSavedInVancouver() {
        //22.167 million non-vegetarians in Vancouver
        float CO2eSavedInVancouver = CO2eSaved * 22.167f;
        return CO2eSavedInVancouver;
    }

    //Returns the amount of gasoline needed to create an equivalent amount of CO2
    public float getEquivalentLitresOfGasoline() {
        //2.31 kg of CO2 per L of gasoline burned
        float litresOfGasoline = CO2eSaved/2.31f;
        return litresOfGasoline;
    }

    //Sets CO2eSaved
    public void setCO2eSaved(float saved) {
        CO2eSaved = saved;
    }
}
