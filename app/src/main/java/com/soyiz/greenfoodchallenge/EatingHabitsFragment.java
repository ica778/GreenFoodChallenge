package com.soyiz.greenfoodchallenge;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EatingHabitsFragment extends Fragment implements View.OnClickListener

{

    private EditText et_beef;
    private EditText et_chicken;
    private EditText et_pork;
    private EditText et_fish;
    private EditText et_beans;
    private EditText et_vegetables;
    private EditText et_egg;
    private Button btn_total;
    private TextView tv_result;
    private Integer dBeef;
    private Integer dChicken;
    private Integer dPork;
    private Integer dFish;
    private Integer dBeans;
    private Integer dVegetables;
    private Integer dEgg;

    public EatingHabitsFragment() {


    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, EatingHabitsFragment.class);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
        et_beans = (EditText) view.findViewById(R.id.et_beans);
        et_vegetables = (EditText) view.findViewById(R.id.et_vegetables);
        et_egg = (EditText) view.findViewById(R.id.et_egg);
        btn_total = view.findViewById(R.id.btn_total);
        tv_result = view.findViewById(R.id.tv_result);
        btn_total.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String beef = et_beef.getText().toString().trim();
        if (TextUtils.isEmpty(beef)) {
            Toast.makeText(getContext(), "beef cannot be empty", Toast.LENGTH_SHORT).show();
            return;
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

        String beans = et_beans.getText().toString().trim();
        if (TextUtils.isEmpty(beans)) {
            Toast.makeText(getContext(), "beans cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String vegetables = et_vegetables.getText().toString().trim();
        if (TextUtils.isEmpty(vegetables)) {
            Toast.makeText(getContext(), "vegetables cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String egg = et_egg.getText().toString().trim();
        if (TextUtils.isEmpty(egg)) {
            Toast.makeText(getContext(), "egg cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        dBeef = Integer.valueOf(beef);
        dChicken = Integer.valueOf(chicken);
        dPork = Integer.valueOf(pork);
        dFish = Integer.valueOf(fish);
        dBeans = Integer.valueOf(beans);
        dVegetables = Integer.valueOf(vegetables);
        dEgg = Integer.valueOf(egg);


        Float total = 365*(27 * dBeef + 12.1F * dPork + 6.9F * dChicken + 6.1F * dFish + 4.8F * dEgg + 2 * dBeans + 2 * dVegetables)/1000;
        Integer i = Math.round(total);
        String stringToShow = getResources().getString(R.string.co2_100g_n);
        float regionAverageC02eConsumption = 7700;
        String howDoesUsersDietCompare = String.format(stringToShow, i, DietComparer.getHowWellUserComparesToRegion(i, regionAverageC02eConsumption));
        tv_result.setText(howDoesUsersDietCompare);

    }

    @Override
    public void onClick(View view) {
        submit();
        Intent intent = new Intent(getActivity().getBaseContext(), EcoFragment.class);
        intent.putExtra("message", dBeef);
        getActivity().startActivity(intent);
    }
}
