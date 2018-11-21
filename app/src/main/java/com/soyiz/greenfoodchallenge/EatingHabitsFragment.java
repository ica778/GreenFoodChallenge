package com.soyiz.greenfoodchallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class EatingHabitsFragment extends Fragment implements View.OnClickListener {

    private EditText et_beef;
    private EditText et_chicken;
    private EditText et_pork;
    private EditText et_fish;
    private EditText et_bean;
    private EditText et_vegetable;
    private EditText et_egg;
    private Button btn_total;
    private TextView tv_result;
    private ScrollView mScrollView;
    private TextView beefText, chickenText, porkText, fishText, beanText, vegetablesText, eggsText;

    public EatingHabitsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eating_habits, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        et_beef = view.findViewById(R.id.et_beef);
        et_chicken = view.findViewById(R.id.et_chicken);
        et_pork = view.findViewById(R.id.et_pork);
        et_fish = view.findViewById(R.id.et_fish);
        et_bean = view.findViewById(R.id.et_beans);
        et_vegetable = view.findViewById(R.id.et_vegetables);
        et_egg = view.findViewById(R.id.et_egg);

        beefText = view.findViewById(R.id.beefText);
        chickenText = view.findViewById(R.id.chickenText);
        porkText = view.findViewById(R.id.porkText);
        fishText = view.findViewById(R.id.fishText);
        beanText = view.findViewById(R.id.beanText);
        vegetablesText = view.findViewById(R.id.vegetablesText);
        eggsText = view.findViewById(R.id.eggsText);

        beefText.setOnClickListener(this);
        chickenText.setOnClickListener(this);
        porkText.setOnClickListener(this);
        fishText.setOnClickListener(this);
        beanText.setOnClickListener(this);
        vegetablesText.setOnClickListener(this);
        eggsText.setOnClickListener(this);

        btn_total = view.findViewById(R.id.btn_total);
        tv_result = view.findViewById(R.id.tv_result);
        btn_total.setOnClickListener(this);
        mScrollView = view.findViewById(R.id.mScrollView);
        mScrollView.smoothScrollBy(0, 10000);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_total:
                if (userInput() == true) {
                    CalculatorResultsFragment nextFragment= new CalculatorResultsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(((ViewGroup)getView().getParent()).getId(), nextFragment,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.calculator_invalid_input_toast), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.beefText:
                Toast.makeText(getContext(), getResources().getString(R.string.beefServingsGuide), Toast.LENGTH_SHORT).show();
            break;
            case R.id.chickenText:
                Toast.makeText(getContext(), getResources().getString(R.string.chickenServingsGuide), Toast.LENGTH_SHORT).show();
                break;
            case R.id.porkText:
                Toast.makeText(getContext(), getResources().getString(R.string.porkServingsGuide), Toast.LENGTH_SHORT).show();
                break;
            case R.id.fishText:
                Toast.makeText(getContext(), getResources().getString(R.string.fishServingsGuide), Toast.LENGTH_SHORT).show();
                break;
            case R.id.beanText:
                Toast.makeText(getContext(), getResources().getString(R.string.beansServingsGuide), Toast.LENGTH_SHORT).show();
                break;
            case R.id.vegetablesText:
                Toast.makeText(getContext(), getResources().getString(R.string.vegetablesServingsGuide), Toast.LENGTH_SHORT).show();
                break;
            case R.id.eggsText:
                Toast.makeText(getContext(), getResources().getString(R.string.eggsServingsGuide), Toast.LENGTH_SHORT).show();
                break;
        }

        /*
        if (userInput() == true) {
            CalculatorResultsFragment nextFragment= new CalculatorResultsFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(((ViewGroup)getView().getParent()).getId(), nextFragment,"findThisFragment")
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.calculator_invalid_input_toast), Toast.LENGTH_SHORT).show();
        }
        */
    }

    /* This method checks if user has entered at least one valid input which is at least 1 number greater than 0.
     * If they have we set the blank spots to 0 and the part the user inputted as the number they entered. Returns
     * true if they entered at least one valid input and false otherwise. */
    private boolean userInput() {
        String beef = et_beef.getText().toString().trim();
        String chicken = et_chicken.getText().toString().trim();
        String pork = et_pork.getText().toString().trim();
        String fish = et_fish.getText().toString().trim();
        String bean = et_bean.getText().toString().trim();
        String vegetable = et_vegetable.getText().toString().trim();
        String egg = et_egg.getText().toString().trim();
        boolean userHasEnteredInput = false;

        // Put user input into singleton
        if (TextUtils.isEmpty(beef) || Integer.parseInt(beef) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("beef", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("beef",  ((float) (Integer.parseInt(beef)) * (float) ProteinSource.getDailyServing()) / 7f);
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(chicken) || Integer.parseInt(chicken) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("chicken", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("chicken", ((float) (Integer.parseInt(chicken)) * (float) ProteinSource.getDailyServing()) / 7f);
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(pork) || Integer.parseInt(pork) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("pork", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("pork", ((float) (Integer.parseInt(pork)) * (float) ProteinSource.getDailyServing()) / 7f);
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(fish) || Integer.parseInt(fish) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("fish", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("fish", ((float) (Integer.parseInt(fish)) * (float) ProteinSource.getDailyServing()) / 7f);
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(bean) || Integer.parseInt(bean) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("bean", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("bean", ((float) (Integer.parseInt(bean)) * (float) ProteinSource.getDailyServing()) / 7f);
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(vegetable) || Integer.parseInt(vegetable) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("vegetable", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("vegetable", ((float) (Integer.parseInt(vegetable)) * (float) ProteinSource.getDailyServing()) / 7f);
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(egg) || Integer.parseInt(egg) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("egg", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("egg", ((float) (Integer.parseInt(egg)) * (float) ProteinSource.getDailyServing()) / 7f);
            userHasEnteredInput = true;
        }
        return userHasEnteredInput;
    }
}
