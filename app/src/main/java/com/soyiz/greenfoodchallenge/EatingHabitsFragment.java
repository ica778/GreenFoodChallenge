package com.soyiz.greenfoodchallenge;

import android.os.Bundle;
import android.os.CountDownTimer;
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
        btn_total = view.findViewById(R.id.btn_total);
        tv_result = view.findViewById(R.id.tv_result);
        btn_total.setOnClickListener(this);
        mScrollView = view.findViewById(R.id.mScrollView);
        mScrollView.smoothScrollBy(0, 10000);
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
        if (TextUtils.isEmpty(beef) || Integer.parseInt(beef) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("beef", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("beef", Integer.parseInt(beef));
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(chicken) || Integer.parseInt(chicken) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("chicken", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("chicken", Integer.parseInt(chicken));
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(pork) || Integer.parseInt(pork) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("pork", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("pork", Integer.parseInt(pork));
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(fish) || Integer.parseInt(fish) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("fish", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("fish", Integer.parseInt(fish));
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(bean) || Integer.parseInt(bean) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("bean", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("bean", Integer.parseInt(bean));
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(vegetable) || Integer.parseInt(vegetable) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("vegetable", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("vegetable", Integer.parseInt(vegetable));
            userHasEnteredInput = true;
        }
        if (TextUtils.isEmpty(egg) || Integer.parseInt(egg) == 0) {
            UserDietInfo.getInstance().setAmountOfProteinGrams("egg", 0);
        } else {
            UserDietInfo.getInstance().setAmountOfProteinGrams("egg", Integer.parseInt(egg));
            userHasEnteredInput = true;
        }
        return userHasEnteredInput;
    }

    // This method calculates the C02e from the user's input.
    private void calculateUserInput() {
        double total = 365 * (27 * UserDietInfo.getInstance().getAmountOfProteinGrams("beef")
                + 12.1F * UserDietInfo.getInstance().getAmountOfProteinGrams("pork")
                + 6.9F * UserDietInfo.getInstance().getAmountOfProteinGrams("chicken")
                + 6.1F * UserDietInfo.getInstance().getAmountOfProteinGrams("fish")
                + 4.8F * UserDietInfo.getInstance().getAmountOfProteinGrams("egg")
                + 2 * UserDietInfo.getInstance().getAmountOfProteinGrams("bean")
                + 2 * UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable")) / 1000;
        long kgOfC02eInDiet = Math.round(total);
        double tonnesOfC02eInDiet = kgOfC02eInDiet / 1000f;
        String stringToShow = getResources().getString(R.string.calculator_results);
        double regionAverageTonnesC02e = 7.7f; // 7.7 tonnes is per capita average for Vancouver according to lecture notes
        double litresOfGasolineEquivalentToC02e = DietComparer.getLitresOfGasolineEquivalentToDietC02e(kgOfC02eInDiet);
        int currentPopulationOfArea = 2463000; // Current population of metro vancouver
        String howDoesUsersDietCompare = String.format(stringToShow,
                tonnesOfC02eInDiet,
                litresOfGasolineEquivalentToC02e,
                regionAverageTonnesC02e,
                DietComparer.getHowWellC02eComparesToAverage(kgOfC02eInDiet, regionAverageTonnesC02e * 1000),
                Math.round(currentPopulationOfArea * tonnesOfC02eInDiet),
                DietComparer.getHowWellC02eComparesToAverage(currentPopulationOfArea * tonnesOfC02eInDiet, regionAverageTonnesC02e * currentPopulationOfArea),
                Math.round(currentPopulationOfArea * regionAverageTonnesC02e));
        tv_result.setText(howDoesUsersDietCompare);
    }

    @Override
    public void onClick(View view) {
        if (userInput() == true) {
            calculateUserInput();
            new CountDownTimer(100, 100) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    mScrollView.smoothScrollBy(0, 10000);
                }
            }.start();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.calculator_invalid_input_toast), Toast.LENGTH_SHORT).show();
        }
    }

}
