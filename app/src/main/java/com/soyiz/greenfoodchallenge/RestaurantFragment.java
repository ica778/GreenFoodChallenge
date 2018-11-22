package com.soyiz.greenfoodchallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class RestaurantFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView = null;
    private List<MealCard> mealCardList = new ArrayList<>();
    private Button testButton;
    private Button searchButton;
    private EditText searchBar;

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
        //so latest additions shown at the top
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mealCardList);
        recyclerView.setAdapter(adapter);

        initView(view);
        return view;
    }

    private void initView(View view) {
        testButton = view.findViewById(R.id.test_button);
        testButton.setOnClickListener(this);
        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
        searchBar = view.findViewById(R.id.search_bar);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_button:
                createTestMeals();
                break;
            case R.id.search_button:
                search();
                break;
        }
    }

    public void postMeal(MealCard mealCard) {
        mealCardList.add(mealCard);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void deleteMeal(MealCard mealCard) {
        mealCardList.remove(mealCard);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void createTestMeals() {

    }
    // Using the keyword from search bar to search restaurant.
    public void search(){
        mealCardList.clear();
        Map mealList = getRestaurants();
        for (Object key : mealList.keySet()) {
            MealCard tempMealCard = (MealCard) mealList.get( key);
            mealCardList.add(tempMealCard);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    //Todo: query server by keyword to get restaurants - zhiwen
    public Map<String, MealCard> getRestaurants() {
        Map mealList = new HashMap();
        MealCard testMeal4 = new MealCard();
        testMeal4.setMealName("Burger");
        MealCard testMeal5 = new MealCard();
        testMeal5.setMealName("Noodle");
        mealList.put("b",testMeal4);
        mealList.put("n",testMeal5);


        return mealList;
    }
}
