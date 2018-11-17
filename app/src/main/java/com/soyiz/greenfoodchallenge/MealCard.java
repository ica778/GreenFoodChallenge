package com.soyiz.greenfoodchallenge;

public class MealCard {

    private String mealName = null;
    private String mealProtein = null;
    private String restaurantName = null;
    private String restaurantLocation = null;
    private int mealImageId;
    private String description = null;

    MealCard() {

    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealProtein() {
        return mealProtein;
    }

    public void setMealProtein(String mealProtein) {
        this.mealProtein = mealProtein;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public int getMealImageId() {
        return mealImageId;
    }

    public void setMealImageId(int mealImageId) {
        this.mealImageId = mealImageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
