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
        et_beef = (EditText) view.findViewById(R.id.et_beef);
        et_chicken = (EditText) view.findViewById(R.id.et_chicken);
        et_pork = (EditText) view.findViewById(R.id.et_pork);
        et_fish = (EditText) view.findViewById(R.id.et_fish);
        et_bean = (EditText) view.findViewById(R.id.et_beans);
        et_vegetable = (EditText) view.findViewById(R.id.et_vegetables);
        et_egg = (EditText) view.findViewById(R.id.et_egg);
        btn_total = view.findViewById(R.id.btn_total);
        tv_result = view.findViewById(R.id.tv_result);
        btn_total.setOnClickListener(this);
        mScrollView = (ScrollView) view.findViewById(R.id.mScrollView);
        mScrollView.smoothScrollBy(0,10000);
    }

    private void submitUserInput() {
        // validate
        String beef = et_beef.getText().toString().trim();
        if (TextUtils.isEmpty(beef)) {
            Toast.makeText(getContext(), "beef cannot be empty", Toast.LENGTH_SHORT).show();
            ;
        }
        String chicken = et_chicken.getText().toString().trim();
        if (TextUtils.isEmpty(chicken)) {
            Toast.makeText(getContext(), "chicken cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String pork = et_pork.getText().toString().trim();
        if (TextUtils.isEmpty(pork)) {
            Toast.makeText(getContext(), "pork cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String fish = et_fish.getText().toString().trim();
        if (TextUtils.isEmpty(fish)) {
            Toast.makeText(getContext(), "fish cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String bean = et_bean.getText().toString().trim();
        if (TextUtils.isEmpty(bean)) {
            Toast.makeText(getContext(), "bean cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String vegetable = et_vegetable.getText().toString().trim();
        if (TextUtils.isEmpty(vegetable)) {
            Toast.makeText(getContext(), "vegetable cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String egg = et_egg.getText().toString().trim();
        if (TextUtils.isEmpty(egg)) {
            Toast.makeText(getContext(), "egg cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDietInfo.getInstance().setAmountOfProteinGrams("beef", Integer.parseInt(beef));
        UserDietInfo.getInstance().setAmountOfProteinGrams("chicken", Integer.parseInt(chicken));
        UserDietInfo.getInstance().setAmountOfProteinGrams("pork", Integer.parseInt(pork));
        UserDietInfo.getInstance().setAmountOfProteinGrams("fish", Integer.parseInt(fish));
        UserDietInfo.getInstance().setAmountOfProteinGrams("bean", Integer.parseInt(bean));
        UserDietInfo.getInstance().setAmountOfProteinGrams("vegetable", Integer.parseInt(vegetable));
        UserDietInfo.getInstance().setAmountOfProteinGrams("egg", Integer.parseInt(egg));

        float total = 365 * (27 * UserDietInfo.getInstance().getAmountOfProteinGrams("beef")
                + 12.1F * UserDietInfo.getInstance().getAmountOfProteinGrams("pork")
                + 6.9F * UserDietInfo.getInstance().getAmountOfProteinGrams("chicken")
                + 6.1F * UserDietInfo.getInstance().getAmountOfProteinGrams("fish")
                + 4.8F * UserDietInfo.getInstance().getAmountOfProteinGrams("egg")
                + 2 * UserDietInfo.getInstance().getAmountOfProteinGrams("bean")
                + 2 * UserDietInfo.getInstance().getAmountOfProteinGrams("vegetable")) / 1000;
        Integer kgOfC02eInDiet = Math.round(total);
        float tonnesOfC02eInDiet = kgOfC02eInDiet / 1000f;
        String stringToShow = getResources().getString(R.string.co2_100g_n);
        float regionAverageTonnesC02e = 7.7f; // 7.7 tonnes is per capita average for Vancouver according to lecture notes
        float litresOfGasolineEquivalentToC02e = DietComparer.getLitresOfGasolineEquivalentToDietC02e(kgOfC02eInDiet);
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
        submitUserInput();
        new CountDownTimer(100, 100) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                mScrollView.smoothScrollBy(0,10000);
            }
        }.start();
    }
}
