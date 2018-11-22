package com.soyiz.greenfoodchallenge;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddMealDialogFragment extends DialogFragment implements View.OnClickListener, AddMealInterface {

    private Button exitButton;
    private Button addMealButton;
    private Button addImageButton;

    private EditText mealNameEdit;
    private EditText mealProteinEdit;
    private EditText restaurantNameEdit;
    private EditText restaurantLocationEdit;
    private EditText descriptionEdit;

    private String mealName;
    private String mealProtein;
    private String restaurantName;
    private String restaurantLocation;
    private String description;
    private Uri mealImageUri;

    private boolean imageAdded = false;

    private FirebaseHelper.Functions functions = (new FirebaseHelper()).getFunctions();
    private FirebaseHelper.Storage storage = (new FirebaseHelper()).getStorage();

    private static int RESULT_LOAD_IMAGE = 121;

    public AddMealDialogFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_add_meal, new LinearLayout(getActivity()), false);

        exitButton = view.findViewById(R.id.exit_dialog_btn);
        addMealButton = view.findViewById(R.id.add_meal_btn);
        addImageButton = view.findViewById(R.id.add_image_btn);

        initView(view);

        // Build dialog
        Dialog dialogBuilder = new Dialog(getActivity());
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setContentView(view);
        return dialogBuilder;
    }

    private void initView(View view) {
        exitButton = view.findViewById(R.id.exit_dialog_btn);
        exitButton.setOnClickListener(this);
        addMealButton = view.findViewById(R.id.add_meal_btn);
        addMealButton.setOnClickListener(this);
        addImageButton = view.findViewById(R.id.add_image_btn);
        addImageButton.setOnClickListener(this);
        mealNameEdit = view.findViewById(R.id.meal_name_edit);
        mealProteinEdit = view.findViewById(R.id.meal_protein_edit);
        restaurantNameEdit = view.findViewById(R.id.restaurant_name_edit);
        restaurantLocationEdit = view.findViewById(R.id.restaurant_location_edit);
        descriptionEdit = view.findViewById(R.id.description_edit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //exit button
            case R.id.exit_dialog_btn:
                dismiss();
                break;

            //add inputted meal
            case R.id.add_meal_btn:
                MealCard newMeal = new MealCard();
                boolean isComplete = !userInput(newMeal);
                if (isComplete) {
                    String uuid = UUID.randomUUID().toString();
                    newMeal.setUuid(uuid);
                    if(imageAdded) {
                        //add image URI and meal UUID
                        storage.putMealImage(mealImageUri, uuid);
                    }

                    newMeal.setCreator(getUserEmail());

                    functions.setMeal(newMeal);
                    ((AddMealInterface)getTargetFragment()).addMeal();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.add_meal_invalid_input_toast)
                            , Toast.LENGTH_SHORT).show();
                }
                break;

            //get image URI
            case R.id.add_image_btn:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                if (mealImageUri!= null) {
                    imageAdded = true;
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            mealImageUri = data.getData();
        }
    }

    public static AddMealDialogFragment newInstance() {
        AddMealDialogFragment fragment = new AddMealDialogFragment();
        return fragment;
    }

    public boolean userInput(MealCard meal) {
        mealName = mealNameEdit.getText().toString().trim();
        mealProtein = mealProteinEdit.getText().toString().trim();
        restaurantName = restaurantNameEdit.getText().toString().trim();
        restaurantLocation = restaurantLocationEdit.getText().toString().trim();
        description = descriptionEdit.getText().toString().trim();

        meal.setMealName(mealName);
        meal.setMealProtein(mealProtein);
        meal.setRestaurantName(restaurantName);
        meal.setRestaurantLocation(restaurantLocation);
        meal.setMealDescription(description);

        //is true if any non-optional values are empty
        boolean userInputEmpty = TextUtils.isEmpty(mealName)||TextUtils.isEmpty(mealProtein)
                ||TextUtils.isEmpty(restaurantName)||TextUtils.isEmpty(restaurantLocation);
        return userInputEmpty;
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
                return profile.getEmail();
            }
        }
        return null;
    }

    //Interface method
    public void addMeal(/*String uuid*/) {}

}
