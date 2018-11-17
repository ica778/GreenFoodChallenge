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

import java.util.ArrayList;
import java.util.List;


public class RestaurantFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView = null;
    private List<MealCard> mealCardList = new ArrayList<>();
    //will be used so user can delete their meals without searching for them
    private List<MealCard> usersMeals = new ArrayList<>();
    private Button testButton;

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
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_button:
                createTestMeals();
                break;
        }
    }

    public void postMeal(MealCard mealCard) {
        usersMeals.add(mealCard);
        mealCardList.add(mealCard);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void deleteMeal(MealCard mealCard) {
        usersMeals.remove(mealCard);
        mealCardList.remove(mealCard);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void createTestMeals() {
        //Just for testing
        MealCard testMeal1 = new MealCard();
        testMeal1.setMealName("Meal Name 1");
        testMeal1.setMealProtein("Main Protein of Meal 1");
        testMeal1.setRestaurantName("Restaurant Name 1");
        testMeal1.setRestaurantLocation("Restaurant Location 1");
        testMeal1.setMealImageId(R.drawable.ic_pledge_icon_24dp);
        testMeal1.setDescription("afsdfjlsa fiodsjfsdaklf kjfl ajf l flj laskfdj ldf sd.sf jldasf jsdlf jldf dla " +
                "sdf sdfj dskafj lsdlfj ldsfj ldsjf dlsfj alsd");;
        MealCard testMeal2 = new MealCard();
        testMeal2.setMealName("Meal Name 2");
        testMeal2.setMealProtein("Main Protein of Meal 2");
        testMeal2.setRestaurantName("Restaurant Name 2");
        testMeal2.setRestaurantLocation("Restaurant Location 2");
        testMeal2.setMealImageId(R.drawable.ic_pledge_icon_24dp);
        testMeal2.setDescription("afsdfjlsa fiodsjfsdaklf kjfl ajf l flj laskfdj ldf sd.sf jldasf jsdlf jldf dla " +
                "sdf sdfj dskafj lsdlfj ldsfj ldsjf dlsfj alsd");
        MealCard testMeal3 = new MealCard();
        testMeal3.setMealName("Meal Name 3");
        testMeal3.setMealProtein("Main Protein of Meal 3");
        testMeal3.setRestaurantName("Restaurant Name 3");
        testMeal3.setRestaurantLocation("Restaurant Location 3");
        postMeal(testMeal1);
        postMeal(testMeal2);
        postMeal(testMeal3);
    }

}
