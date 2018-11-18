package com.soyiz.greenfoodchallenge;

public class MealCard {

    private String mealName = null;
    private String mealProtein = null;
    private String restaurantName = null;
    private String restaurantLocation = null;
    //This icon will show if no image is chosen instead of no image at all
    private String description = null;
    private boolean imageAdded = false;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isImageAdded() {
        return imageAdded;
    }

    public void setImageAdded(boolean imageAdded) {
        this.imageAdded = imageAdded;
    }
}
