package com.soyiz.greenfoodchallenge;


public class PledgeCard {

    private String userName = null;
    private double pledgeAmount = 0.0;


    PledgeCard() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public double getPledgeAmount() {
        return pledgeAmount;
    }

    public void setPledgeAmount(double pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

}
