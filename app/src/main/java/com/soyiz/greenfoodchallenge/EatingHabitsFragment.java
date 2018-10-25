package com.soyiz.greenfoodchallenge;


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

    public EatingHabitsFragment() {


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

        Integer dBeef = Integer.valueOf(beef);
        Integer dChicken = Integer.valueOf(chicken);
        Integer dPork = Integer.valueOf(pork);
        Integer dFish = Integer.valueOf(fish);
        Integer dBeans = Integer.valueOf(beans);
        Integer dVegetables = Integer.valueOf(vegetables);
        Integer dEgg = Integer.valueOf(egg);


        Float total = 365*(27 * dBeef + 12.1F * dPork + 6.9F * dChicken + 6.1F * dFish + 4.8F * dEgg + 2 * dBeans + 2 * dVegetables)/1000;
        Integer i = Math.round(total);
        String a = getResources().getString(R.string.co2_100g_n);
        String b = "";// different output according to the total co2e compared to the regional average
        if (total <= 7800 * 0.75) {
            b = String.format(a, i, "Much better than");
        } else if(total <= 7800*0.9) {
            b = String.format(a, i, "Better than");
        } else if (total <= 1.1 * 7800) {
            b = String.format(a,i,"Equals to");
        }
        else if (total <= 1.1 * 7800) {
            b = String.format(a,i,"Worse than");
        }
        else{
            b = String.format(a,i,"Much Worse than");
        }

        tv_result.setText(b);

    }

    @Override
    public void onClick(View view) {
        submit();
    }
}
