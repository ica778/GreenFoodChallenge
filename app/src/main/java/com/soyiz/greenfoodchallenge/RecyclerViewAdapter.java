package com.soyiz.greenfoodchallenge;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RestaurantViewAdapter extends RecyclerView
        .Adapter<RestaurantViewAdapter.MealCardViewHolder> {

    public static class MealCardViewHolder extends RecyclerView.ViewHolder {
        CardView mealCardView;
        TextView mealName;
        TextView mealProtein;
        TextView restaurantName;
        TextView restaurantLocation;

        MealCardViewHolder(View view) {
            super(view);
            mealCardView = (CardView)view.findViewById(R.id.meal_card_view);
            mealName = (TextView)view.findViewById(R.id.meal_name);
            mealProtein = (TextView)view.findViewById(R.id.meal_protein);
            restaurantName = (TextView)view.findViewById(R.id.restaurant_name);
            restaurantLocation = (TextView)view.findViewById(R.id.restaurant_location);
        }
    }

    List<MealCard> mealCardList;
    RestaurantViewAdapter(List<MealCard> mealCardList) {
        this.mealCardList = mealCardList;
    }

    @Override
    public int getItemCount() {
        return mealCardList.size();
    }

    @Override
    public MealCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meal_card_view, viewGroup, false);
        MealCardViewHolder mealCardViewHolder = new MealCardViewHolder(view);
        return mealCardViewHolder;
    }

    @Override
    public void onBindViewHolder(MealCardViewHolder mealCardViewHolder, int i) {
        mealCardViewHolder.mealName.setText(mealCardList.get(i).getMealName());
        mealCardViewHolder.mealProtein.setText(mealCardList.get(i).getMealProtein());
        mealCardViewHolder.restaurantName.setText(mealCardList.get(i).getRestaurantName());
        mealCardViewHolder.restaurantLocation.setText(mealCardList.get(i).getRestaurantLocation());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
