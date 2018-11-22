package com.soyiz.greenfoodchallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.io.File;
import java.util.List;

import static java.security.AccessController.getContext;

public class RecyclerViewAdapter extends RecyclerView
        .Adapter<RecyclerViewAdapter.MealCardViewHolder> {

    private int expandedPosition = -1;
    private List<MealCard> mealCardList;
    private FirebaseHelper.Storage storage = (new FirebaseHelper()).getStorage();
    private FirebaseHelper.Functions functions = (new FirebaseHelper()).getFunctions();
    private File file = null;

    String TAG = "RecyclerViewAdapter";

    public static class MealCardViewHolder extends RecyclerView.ViewHolder {
        CardView mealCardView;
        TextView mealName;
        TextView mealProtein;
        TextView restaurantName;
        TextView restaurantLocation;
        ImageView mealImage;
        TextView mealDescription;
        Button deleteButton;


        MealCardViewHolder(View view) {
            super(view);
            mealCardView = view.findViewById(R.id.meal_card_view);
            mealName = view.findViewById(R.id.meal_name);
            mealProtein = view.findViewById(R.id.meal_protein);
            restaurantName = view.findViewById(R.id.restaurant_name);
            restaurantLocation = view.findViewById(R.id.restaurant_location);
            mealImage = view.findViewById(R.id.meal_image);
            mealDescription = view.findViewById(R.id.description);
            deleteButton = view.findViewById(R.id.delete_meal_btn);
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
    public void onBindViewHolder(MealCardViewHolder mealCardViewHolder, final int position) {
        mealCardViewHolder.mealName.setText(mealCardList.get(position).getMealName());
        mealCardViewHolder.mealProtein.setText(mealCardList.get(position).getMealProtein());
        mealCardViewHolder.restaurantName.setText(mealCardList.get(position).getRestaurantName());
        mealCardViewHolder.restaurantLocation.setText(mealCardList.get(position).getRestaurantLocation());
        mealCardViewHolder.mealDescription.setText(mealCardList.get(position).getMealDescription());
        mealCardViewHolder.deleteButton.setText(R.string.delete_meal_card_text);

        //When image is added by user during creation of a meal, file != null;
        String uuid = mealCardList.get(position).getUuid();
        storage.getMealImage(uuid,this::setFile);
        if (file != null) {
            //retrieve image from database
            //convert to bitmap
            String path = file.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            //set image
            mealCardViewHolder.mealImage.setImageBitmap(bitmap);
        } else {
            mealCardViewHolder.mealImage.setImageResource(R.drawable.ic_restaurant_icon_24dp);
        }

        final boolean isExpanded = (position == expandedPosition);
        if (isExpanded) {
            mealCardViewHolder.mealDescription.setVisibility(View.VISIBLE);
            mealCardViewHolder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            mealCardViewHolder.mealDescription.setVisibility(View.GONE);
            mealCardViewHolder.deleteButton.setVisibility(View.GONE);
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
        mealCardViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, getUserEmail() + " and " + mealCardList.get(position).getCreator());
               //if (getUserEmail() == mealCardList.get(position).getCreator()) {
                   functions.deleteMeal(mealCardList.get(position));
                   mealCardList.remove(mealCardList.get(position));
                   notifyItemRemoved(position);
               //}
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    //In order to get image to convert to get path
    public void setFile(File file) {
        this.file = file;
    }

    public String getUserEmail() {
        User user = User.getCurrent();
        FirebaseUser firebaseUser = user.getFirebaseUser();
        // Get the user email by looping over the providers and grabbing the first email listed there
        for (UserInfo profile : firebaseUser.getProviderData()) {
            // Skip firebase as a provider
            if (profile.getProviderId().equals("firebase")) {
                continue;
            }

            if (profile.getEmail() != null) {
                Log.d(TAG, "findUserEmail: user email '" + profile.getEmail() + "' found on provider '" + profile.getProviderId() + "'");
                return profile.getEmail();
            }
        }
        Log.e(TAG, "findUserEmail: no valid user email found! Bad bad things are happening!");
        return null;
    }

}
