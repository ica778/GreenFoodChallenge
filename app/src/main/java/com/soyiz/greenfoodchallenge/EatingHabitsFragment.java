package com.soyiz.greenfoodchallenge;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class EatingHabitsFragment extends Fragment

{
    public static final String EXTRA_NUMBER="com.example.application.example.EXTRA_NUMBER";
    public EatingHabitsFragment()
    {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_eating_habits, container, false);
        EditText chicken = (EditText) v.findViewById(R.id.chickenedit);
        EditText beef = (EditText) v.findViewById(R.id.beefedit);
        EditText pork = (EditText) v.findViewById(R.id.porkedit);
        EditText egg = (EditText) v.findViewById(R.id.eggedit);
        EditText fish = (EditText) v.findViewById(R.id.fishedit);
        EditText beans = (EditText) v.findViewById(R.id.beansedit);
        EditText vegetables = (EditText) v.findViewById(R.id.vegetablesedit);
        double chickennum = Double.parseDouble(chicken.getText().toString());
        double beefnum = Double.parseDouble(beef.getText().toString());
        double porknum = Double.parseDouble(pork.getText().toString());
        double eggnum = Double.parseDouble(egg.getText().toString());
        double fishnum = Double.parseDouble(fish.getText().toString());
        double beansnum = Double.parseDouble(beans.getText().toString());
        double vegetablesnum = Double.parseDouble(vegetables.getText().toString());




        return inflater.inflate(R.layout.fragment_eating_habits, container, false);
    }
}
