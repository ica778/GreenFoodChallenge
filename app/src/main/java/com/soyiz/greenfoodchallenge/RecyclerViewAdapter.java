package com.soyiz.greenfoodchallenge;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView
        .Adapter<RecyclerViewAdapter.MealCardViewHolder> {

    private int expandedPosition = -1;
    private List<MealCard> mealCardList;

    public static class MealCardViewHolder extends RecyclerView.ViewHolder {
        CardView mealCardView;
        TextView mealName;
        TextView mealProtein;
        TextView restaurantName;
        TextView restaurantLocation;
        ImageView mealImage;
        TextView mealDescription;


        MealCardViewHolder(View view) {
            super(view);
            mealCardView = view.findViewById(R.id.meal_card_view);
            mealName = view.findViewById(R.id.meal_name);
            mealProtein = view.findViewById(R.id.meal_protein);
            restaurantName = view.findViewById(R.id.restaurant_name);
            restaurantLocation = view.findViewById(R.id.restaurant_location);
            mealImage = view.findViewById(R.id.meal_image);
            mealDescription = view.findViewById(R.id.description);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    RecyclerViewAdapter(List<MealCard> mealCardList) {
        this.mealCardList = mealCardList;
    }

    @Override
    public int getItemCount() {
        return mealCardList.size();
    }

    @Override
    public MealCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meal_card_view, viewGroup, false);
        return new MealCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MealCardViewHolder mealCardViewHolder, int position) {
        mealCardViewHolder.mealName.setText(mealCardList.get(position).getMealName());
        mealCardViewHolder.mealProtein.setText(mealCardList.get(position).getMealProtein());
        mealCardViewHolder.restaurantName.setText(mealCardList.get(position).getRestaurantName());
        mealCardViewHolder.restaurantLocation.setText(mealCardList.get(position).getRestaurantLocation());
        mealCardViewHolder.mealDescription.setText(mealCardList.get(position).getMealDescription());
        //When image is added by user during creation of a meal, isImageAdded() == true;
        if (mealCardList.get(position).isImageAdded()) {
            //to be implemented along with meal adding from dialog
            //retrieve image from database
        } else {
            mealCardViewHolder.mealImage.setImageResource(R.drawable.ic_restaurant_icon_24dp);
        }

        final boolean isExpanded = (position == expandedPosition);
        if (isExpanded) {
            mealCardViewHolder.mealDescription.setVisibility(View.VISIBLE);
        } else {
            mealCardViewHolder.mealDescription.setVisibility(View.GONE);
        }
        mealCardViewHolder.itemView.setActivated(isExpanded);

        final int pos = position;
        mealCardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    expandedPosition = -1;
                } else {
                    expandedPosition = pos;
                }
                notifyItemChanged(pos);
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
