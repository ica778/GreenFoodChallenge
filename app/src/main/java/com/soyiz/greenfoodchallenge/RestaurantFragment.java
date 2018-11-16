package com.soyiz.greenfoodchallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFragment extends Fragment {

    private RecyclerView recyclerView = null;
    private List<MealCard> mealCardList = new ArrayList<>();

    public RestaurantFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(manager);

        //Just for testing
        MealCard testMeal1 = new MealCard();
        testMeal1.setMealName("Meal Name 1");
        testMeal1.setMealProtein("Main Protein of Meal 1");
        testMeal1.setRestaurantName("Restaurant Name 1");
        testMeal1.setRestaurantLocation("Restaurant Location 1");
        mealCardList.add(testMeal1);
        MealCard testMeal2 = new MealCard();
        testMeal2.setMealName("Meal Name 2");
        testMeal2.setMealProtein("Main Protein of Meal 2");
        testMeal2.setRestaurantName("Restaurant Name 2");
        testMeal2.setRestaurantLocation("Restaurant Location 2");
        mealCardList.add(testMeal2);
        MealCard testMeal3 = new MealCard();
        testMeal3.setMealName("Meal Name 3");
        testMeal3.setMealProtein("Main Protein of Meal 3");
        testMeal3.setRestaurantName("Restaurant Name 3");
        testMeal3.setRestaurantLocation("Restaurant Location 3");
        mealCardList.add(testMeal3);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mealCardList);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
