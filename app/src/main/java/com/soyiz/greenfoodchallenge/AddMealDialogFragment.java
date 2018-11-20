package com.soyiz.greenfoodchallenge;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.*;

public class AddMealDialogFragment extends DialogFragment implements View.OnClickListener, AddMealInterface {

    private Button exitButton;
    private Button tempAddMeal;
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

    public AddMealDialogFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_add_meal, new LinearLayout(getActivity()), false);

        exitButton = (Button) view.findViewById(R.id.exit_dialog_btn);
        tempAddMeal = (Button) view.findViewById(R.id.temp_add_meal);

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
        tempAddMeal = view.findViewById(R.id.temp_add_meal);
        tempAddMeal.setOnClickListener(this);
        mealNameEdit = view.findViewById(R.id.meal_name_edit);
        mealProteinEdit = view.findViewById(R.id.meal_protein_edit);
        restaurantNameEdit = view.findViewById(R.id.restaurant_name_edit);
        restaurantLocationEdit = view.findViewById(R.id.restaurant_location_edit);
        descriptionEdit = view.findViewById(R.id.description_edit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_dialog_btn:
                dismiss();
                break;

            case R.id.temp_add_meal:
                MealCard newMeal = new MealCard();
                boolean isComplete = !userInput(newMeal);
                if (isComplete) {
                    ((AddMealInterface)getTargetFragment()).addMeal(newMeal);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.add_meal_invalid_input_toast)
                            , Toast.LENGTH_SHORT).show();
                }
                break;
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
        meal.setDescription(description);

        //is true if any non-optional values are empty
        boolean userInputEmpty = TextUtils.isEmpty(mealName)||TextUtils.isEmpty(mealProtein)
                ||TextUtils.isEmpty(restaurantName)||TextUtils.isEmpty(restaurantLocation);
        return userInputEmpty;
    }

    //Interface method
    public void addMeal(MealCard newMeal) {}

}
