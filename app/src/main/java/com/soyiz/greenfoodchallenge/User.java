package com.soyiz.greenfoodchallenge;

public class User {

    private Diet currentDiet = new Diet();
    private Diet goalDiet = new Diet();
    private Pledge currentPledge = new Pledge();
    private int municipalityStringId = R.string.no_municipality;
    private int userNameStringId = R.string.no_user_name;

    User(){}

    public int getMunicipalityStringId() {
        return municipalityStringId;
    }

    public void setMunicipalityStringId(int municipality) {
        municipalityStringId = municipality;
    }

    public int getUserNameStringId() {
        return userNameStringId;
    }

    public void setUserName(int nameId) {
        userNameStringId = nameId;
    }

    public Diet getCurrentDiet() {
        return currentDiet;
    }

    public void setCurrentDiet(Diet diet) {
        currentDiet = diet;
    }

    public Diet getGoalDiet() {
        return goalDiet;
    }

    public void setGoalDiet(Diet diet) {
        goalDiet = diet;
    }

    public Pledge getCurrentPledge() {
        return currentPledge;
    }

    public void setCurrentPledge(Pledge pledge) {
        currentPledge = pledge;
    }

    public void removePledge() {
        currentPledge = new Pledge();
    }
}
