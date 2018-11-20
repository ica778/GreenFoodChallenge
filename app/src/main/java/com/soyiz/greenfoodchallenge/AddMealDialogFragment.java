package com.soyiz.greenfoodchallenge;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddMealDialogFragment extends DialogFragment implements View.OnClickListener, AddMealInterface {

    private Button exitButton;
    private Button tempAddMeal;

    public AddMealDialogFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_add_meal, new LinearLayout(getActivity()), false);

        exitButton = (Button) view.findViewById(R.id.exit_dialog);
        tempAddMeal = (Button) view.findViewById(R.id.temp_add_meal);

        initView(view);

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    private void initView(View view) {
        exitButton = view.findViewById(R.id.exit_dialog);
        exitButton.setOnClickListener(this);
        tempAddMeal = view.findViewById(R.id.temp_add_meal);
        tempAddMeal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_dialog:
                dismiss();
                break;

            case R.id.temp_add_meal:
                createTestMeal();
                break;
        }
    }

    public static AddMealDialogFragment newInstance() {
        AddMealDialogFragment fragment = new AddMealDialogFragment();
        return fragment;
    }

    //Interface method
    public void addMeal(MealCard newMeal) {}

    public void createTestMeal() {
        //Just for testing
        MealCard testMeal1 = new MealCard();
        testMeal1.setMealName("Meal Name 1");
        testMeal1.setMealProtein("Main Protein of Meal 1");
        testMeal1.setRestaurantName("Restaurant Name 1");
        testMeal1.setRestaurantLocation("Restaurant Location 1");
        testMeal1.setDescription("afsdfjlsa fiodsjfsdaklf kjfl ajf l flj laskfdj ldf sd.sf jldasf jsdlf jldf dla " +
                "sdf sdfj dskafj lsdlfj ldsfj ldsjf dlsfj alsd");

        ((AddMealInterface)getTargetFragment()).addMeal(testMeal1);
    }

}
