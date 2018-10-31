package com.soyiz.greenfoodchallenge;

public class User {

    private Diet currentDiet = new Diet();
    private Diet goalDiet = new Diet();
    private Pledge currentPledge = new Pledge();
    private int municipalityStringId = R.string.no_municipality;
    private String userName;

    public User(){}

    public User(Diet current, Diet goal, Pledge pledge) {
        currentDiet = current;
        goalDiet = goal;
        currentPledge = pledge;
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

    public int getMunicipalityStringId() {
        return municipalityStringId;
    }

    public void setMunicipalityStringId(int municipality) {
        municipalityStringId = municipality;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        userName = name;
    }
}
