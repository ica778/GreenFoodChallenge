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

    public static class MealCardViewHolder extends RecyclerView.ViewHolder {
        CardView mealCardView;
        TextView mealName;
        TextView mealProtein;
        TextView restaurantName;
        TextView restaurantLocation;
        ImageView mealImage;
        TextView description;
        RelativeLayout expandedArea;


        MealCardViewHolder(View view) {
            super(view);
            mealCardView = (CardView)view.findViewById(R.id.meal_card_view);
            mealName = (TextView)view.findViewById(R.id.meal_name);
            mealProtein = (TextView)view.findViewById(R.id.meal_protein);
            restaurantName = (TextView)view.findViewById(R.id.restaurant_name);
            restaurantLocation = (TextView)view.findViewById(R.id.restaurant_location);
            mealImage = (ImageView)view.findViewById(R.id.meal_image);
            description = (TextView)view.findViewById(R.id.description);
            expandedArea = (RelativeLayout)view.findViewById(R.id.expanded_area);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    List<MealCard> mealCardList;
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
        MealCardViewHolder mealCardViewHolder = new MealCardViewHolder(view);
        return mealCardViewHolder;
    }

    @Override
    public void onBindViewHolder(MealCardViewHolder mealCardViewHolder, int position) {
        mealCardViewHolder.mealName.setText(mealCardList.get(position).getMealName());
        mealCardViewHolder.mealProtein.setText(mealCardList.get(position).getMealProtein());
        mealCardViewHolder.restaurantName.setText(mealCardList.get(position).getRestaurantName());
        mealCardViewHolder.restaurantLocation.setText(mealCardList.get(position).getRestaurantLocation());
        mealCardViewHolder.mealImage.setImageResource(mealCardList.get(position).getMealImageId());
        mealCardViewHolder.description.setText(mealCardList.get(position).getDescription());

        boolean value = (position == expandedPosition);
        final boolean isExpanded = value;
        // if (isExpanded) {.setVisibility(View.VISIBLE)} else {.setVisibility(View.GONE)}
        mealCardViewHolder.expandedArea.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        mealCardViewHolder.itemView.setActivated(isExpanded);

        final int pos = position;
        mealCardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (isExpanded) {expandedPosition = -1} else {expandedPosition = pos}
                expandedPosition = isExpanded ? -1 : pos;
                notifyItemChanged(pos);
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
