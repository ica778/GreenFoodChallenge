package com.soyiz.greenfoodchallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class RestaurantFragment extends Fragment implements View.OnClickListener, AddMealInterface {

    private RecyclerView recyclerView = null;
    private List<MealCard> mealCardList = new ArrayList<>();
    //To make sure meals aren't created twice from Firebase
    private boolean mealListUpdated = false;

    private Button addMealCardButton;
    private Button refreshButton;

    private FirebaseHelper.Functions functions = (new FirebaseHelper()).getFunctions();

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

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mealCardList);
        recyclerView.setAdapter(adapter);

        if (!mealListUpdated) {
            showMeals();
        }

        initView(view);
        return view;
    }

    private void initView(View view) {
        addMealCardButton = view.findViewById(R.id.add_meal_card_button);
        addMealCardButton.setOnClickListener(this);
        refreshButton = view.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_meal_card_button:
                AddMealDialogFragment dialog = AddMealDialogFragment.newInstance();
                dialog.setTargetFragment(this, 0);
                dialog.show(getFragmentManager(), "addMealDialog");
                break;

            //Refreshes page because there is a delay showing info from firebase
            case R.id.refresh_button:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                int fragmentContainer = ((ViewGroup)getView().getParent()).getId();
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(fragmentContainer);
                transaction.detach(fragment);
                transaction.attach(fragment);
                transaction.commit();
        }
    }

    public void showMeals() {
        mealCardList.clear();
        functions.getMealsForList(mealCardList::add);
        recyclerView.getAdapter().notifyDataSetChanged();
        mealListUpdated = true;
    }

    //Interface method
    @Override
    public void addMeal(/*String uuid*/) {
        //functions.getMeal(uuid, mealCardList::add);
        //recyclerView.getAdapter().notifyDataSetChanged();
        mealListUpdated = false;
    }

    public void addNewMealCard(MealCard mealCard) {
        if (!mealCardList.contains(mealCard)) {
            mealCardList.add(mealCard);
        }
    }

}
