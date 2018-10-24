package com.soyiz.greenfoodchallenge;

public class DietsCreator {

    private Diet userDiet;
    private float userDietBeef;
    private float userDietPork;
    private float userDietChicken;
    private float userDietFish;
    private float userDietEggs;
    private float userDietBeans;
    private float userDietVegetables;


    public DietsCreator() {}

    //Sets the base diet to work with
    public void setUserDiet(Diet diet) {
        userDiet = diet;
        userDietBeef = userDiet.getProteinPercent(ProteinSource.Beef);
        userDietPork = userDiet.getProteinPercent(ProteinSource.Pork);
        userDietChicken = userDiet.getProteinPercent(ProteinSource.Chicken);
        userDietFish = userDiet.getProteinPercent(ProteinSource.Fish);
        userDietEggs = userDiet.getProteinPercent(ProteinSource.Eggs);
        userDietBeans = userDiet.getProteinPercent(ProteinSource.Beans);
        userDietVegetables = userDiet.getProteinPercent(ProteinSource.Vegetables);
    }

    //Gets userDiet
    public Diet getUserDiet() {
        return userDiet;
    }

    //Creates diet with same overall meat proportions, but different proportions of each meat type
    public Diet createMeatEaterDiet() {
        /*75% of beef proportions to pork
         *50% of new pork proportions to chicken
         *25% of new chicken proportions to fish*/
        Diet meatEaterDiet = new Diet();

        //Give 25% of user beef proportions to final beef proportions
        float beefInDiet = (float) Math.ceil(userDietBeef*1/4);

        /*Leftover of user beef proportions added with user pork proportions
         * Give 50% of these proportions to final pork proportions*/
        float tempPork = userDietBeef - beefInDiet + userDietPork;
        float porkInDiet = (float) Math.ceil(tempPork*1/2);

        /*Leftover of previous proportions added with user chicken proportions
         * Give 75% of these proportions to final chicken proportions*/
        float tempChicken = tempPork - porkInDiet + userDietChicken;
        float chickenInDiet = (float) Math.ceil(tempChicken*3/4);

        /*Add leftover of previous proportions with user fish proportions
         * Give these proportions to final fish proportions*/
        float fishInDiet = (tempChicken - chickenInDiet + userDietFish);

        //Set final proportions
        meatEaterDiet.setProteinPercent(ProteinSource.Beef, beefInDiet);
        meatEaterDiet.setProteinPercent(ProteinSource.Pork, porkInDiet);
        meatEaterDiet.setProteinPercent(ProteinSource.Chicken, chickenInDiet);
        meatEaterDiet.setProteinPercent(ProteinSource.Fish, fishInDiet);
        //Copy unchanged proportions
        meatEaterDiet.setProteinPercent(ProteinSource.Eggs, userDietEggs);
        meatEaterDiet.setProteinPercent(ProteinSource.Beans, userDietBeans);
        meatEaterDiet.setProteinPercent(ProteinSource.Vegetables, userDietVegetables);

        return meatEaterDiet;
    }

    //Creates diet with lowered proportions of overall meat
    public Diet createLowMeatDiet() {
        /*100% of beef proportions moved to pork
         *75% of new pork proportions moved to chicken
         *50% of new chicken proportions moved to fish and eggs (25/75)
         *25% of each of new fish and eggs proportions go to beans and veg (50/50)*/
        Diet lowMeatDiet = new Diet();

        /*Give 0% of proportions to final beef proportions*/
        float beefInDiet = 0f;

        /*Add user beef proportions with user pork proportions
         * Give 25% of these proportions to final pork proportions*/
        float tempPork = userDietBeef + userDietPork;
        float porkInDiet = (float) Math.ceil(tempPork*1/4);

        /*Add user chicken proportions with leftover proportions
         * Give 50% of these proportions to final chicken proportions
         * Give remaining 50% as leftovers(1) to be split among final fish and egg proportions*/
        float tempChicken = tempPork - porkInDiet + userDietChicken;
        float chickenInDiet = (float) Math.ceil(tempChicken*1/2);
        float proportionLeavingChicken = tempChicken - chickenInDiet;

        /*Add user fish proportions with 25% of leftovers(1)
         * Give 75% of these proportions to final fish proportions
         * Remaining 25% is leftovers(2) to be split among final beans and vegetable proportions*/
        float tempFish = (float) Math.ceil(proportionLeavingChicken*1/4) + userDietFish;
        float fishInDiet = (float) Math.ceil(tempFish*3/4);
        float proportionLeavingFish = tempFish - fishInDiet;

        /*Add user egg proportions with the remaining 75% of leftovers(1)
         * Give 75% of these proportions to final eggs proportions
         * Give remaining 25% as leftovers(3) to be split among final beans and vegetable proportions*/
        float tempEggs = proportionLeavingChicken - (float) Math.ceil(proportionLeavingChicken*1/4) + userDietEggs;
        float eggsInDiet = (float) Math.ceil(tempEggs*3/4);
        float proportionLeavingEggs = tempEggs - eggsInDiet;

        /*Take 50% of leftovers(2) and (3) and add it with user bean proportions summing to
        final bean proportions
        * Add remaining 50% of leftovers (2) and (3) with user vegetable proportions summing
        to final vegetable proportions*/
        float beansInDiet = (float) Math.ceil((proportionLeavingEggs + proportionLeavingFish)*1/2) + userDietBeans;
        float vegetablesInDiet = proportionLeavingEggs + proportionLeavingFish
                - (float) Math.ceil((proportionLeavingEggs + proportionLeavingFish)*1/2) + userDietVegetables;

        //Set final proportions
        lowMeatDiet.setProteinPercent(ProteinSource.Beef, beefInDiet);
        lowMeatDiet.setProteinPercent(ProteinSource.Pork, porkInDiet);
        lowMeatDiet.setProteinPercent(ProteinSource.Chicken, chickenInDiet);
        lowMeatDiet.setProteinPercent(ProteinSource.Fish, fishInDiet);
        lowMeatDiet.setProteinPercent(ProteinSource.Eggs, eggsInDiet);
        lowMeatDiet.setProteinPercent(ProteinSource.Beans, beansInDiet);
        lowMeatDiet.setProteinPercent(ProteinSource.Vegetables, vegetablesInDiet);

        return lowMeatDiet;
    }

    //Create diet with lower overall meat and fish being only meat
    public Diet createOnlyFishDiet() {
        /*100% of all meat but fish get split
         *(40/25/25/10) fish, eggs, beans, and veg*/
        Diet onlyFishDiet = new Diet();

        //Take 100% of beef, pork, and chicken proportions to split among the rest
        float proportionToSplit = userDietBeef + userDietPork + userDietChicken;

        /*Make the fish share 40% of total extra proportions
         * Subtract fish share from the extra proportions*/
        float fishShare = (float) Math.ceil(proportionToSplit*2/5);
        proportionToSplit = proportionToSplit - fishShare;

        /*Make the egg share 25/60 of remaining 60% of extra proportions
         * Subtract egg share from the extra proportions*/
        float eggsShare = (float) Math.ceil(proportionToSplit*5/12);
        proportionToSplit = proportionToSplit - eggsShare;

        /*Make beans share also 25/60 of the previous 60% of extra proportions
         * Subtract beans share from the extra proportions*/
        float beansShare = eggsShare;
        proportionToSplit = proportionToSplit - beansShare;

        //Make vegetable share the remaining extra proportions
        float vegetableShare = proportionToSplit;

        //Make non-fish meat proportions 0%
        onlyFishDiet.setProteinPercent(ProteinSource.Beef, 0);
        onlyFishDiet.setProteinPercent(ProteinSource.Pork, 0);
        onlyFishDiet.setProteinPercent(ProteinSource.Chicken, 0);
        //Set final proportions to the sum of their respective extra proportion shares and user proportions
        onlyFishDiet.setProteinPercent(ProteinSource.Fish, fishShare + userDietFish);
        onlyFishDiet.setProteinPercent(ProteinSource.Eggs, eggsShare + userDietEggs);
        onlyFishDiet.setProteinPercent(ProteinSource.Beans, beansShare + userDietBeans);
        onlyFishDiet.setProteinPercent(ProteinSource.Vegetables, vegetableShare + userDietVegetables);

        return onlyFishDiet;
    }

    //Creates diet with no meat
    public Diet createVegetarianDiet() {
        /*100% of all beef, pork, chicken, and fish
         *gets split (40/40/20) with eggs, beans and veg*/
        Diet vegetarianDiet = new Diet();

        //Take 100% of all meat proportions to split among eggs, beans, and vegetables
        float proportionToSplit = userDietBeef + userDietPork + userDietChicken + userDietFish;

        /*Make the eggs share 40% of total extra proportions
         * Subtract the eggs share from the extra proportions*/
        float eggsShare = (float) Math.ceil(proportionToSplit*2/5);
        proportionToSplit = proportionToSplit - eggsShare;

        /*Make the beans share 40/60 of the remaining 60% of extra proportions
         * Subtract beans share from extra proportions*/
        float beansShare = (float) Math.ceil(proportionToSplit*2/3);
        proportionToSplit = proportionToSplit - beansShare;

        //Make vegetable share the remaining extra proportions
        float vegetableShare = proportionToSplit;

        //Make meat proportions 0%
        vegetarianDiet.setProteinPercent(ProteinSource.Beef, 0);
        vegetarianDiet.setProteinPercent(ProteinSource.Pork, 0);
        vegetarianDiet.setProteinPercent(ProteinSource.Chicken, 0);
        vegetarianDiet.setProteinPercent(ProteinSource.Fish, 0);
        //Set final proportions to the sum of their respective extra proportion shares and user proportions
        vegetarianDiet.setProteinPercent(ProteinSource.Eggs, eggsShare + userDietEggs);
        vegetarianDiet.setProteinPercent(ProteinSource.Beans, beansShare + userDietBeans);
        vegetarianDiet.setProteinPercent(ProteinSource.Vegetables, vegetableShare + userDietVegetables);

        return vegetarianDiet;
    }

    //Creates diet with no meat or eggs
    public Diet createVeganDiet() {
        /*100% of all meats and eggs gets split
         *(80/20) with beans and veg*/
        Diet veganDiet = new Diet();

        //Take 100% of all meat proportions to split among beans and vegetables
        float proportionToSplit = userDietBeef + userDietPork + userDietChicken + userDietFish
                + userDietEggs;

        //Make beans share 80% of extra proportions
        float beansShare = (float) Math.ceil(proportionToSplit*4/5);

        //Make vegetable share the remaining 20% of extra proportions
        float vegetableShare = proportionToSplit - beansShare;

        //Make all meat and egg proportions 0%
        veganDiet.setProteinPercent(ProteinSource.Beef, 0);
        veganDiet.setProteinPercent(ProteinSource.Pork, 0);
        veganDiet.setProteinPercent(ProteinSource.Chicken, 0);
        veganDiet.setProteinPercent(ProteinSource.Fish, 0);
        veganDiet.setProteinPercent(ProteinSource.Eggs, 0);
        //Set final proportions to the sum of their respective extra proportion shares and user proportions
        veganDiet.setProteinPercent(ProteinSource.Beans, beansShare + userDietBeans);
        veganDiet.setProteinPercent(ProteinSource.Vegetables, vegetableShare + userDietVegetables);

        return veganDiet;
    }

}
