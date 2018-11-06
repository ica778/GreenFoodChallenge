package com.soyiz.greenfoodchallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EcoFragment extends Fragment {

    public EcoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View ecoView = inflater.inflate(R.layout.fragment_eco, container, false);

        //TextViews for Eco information display.
        final TextView dietTextView = ecoView.findViewById(R.id.dietInfo);
        final TextView highMeatBetterPercentageView = ecoView
                .findViewById(R.id.highMeatBetterPercentage);
        final TextView lowMeatBetterPercentageView = ecoView
                .findViewById(R.id.lowMeatBetterPercentage);
        final TextView onlyFishBetterPercentageView = ecoView
                .findViewById(R.id.onlyFishBetterPercentage);
        final TextView vegetarianBetterPercentageView = ecoView
                .findViewById(R.id.vegetarianPercentage);
        final TextView veganBetterPercentageView = ecoView.findViewById(R.id.veganBetterPercentage);

        //Buttons in Econ fragment
        Button highMeatButton = ecoView.findViewById(R.id.highMeatButton);
        Button lowMeatButton = ecoView.findViewById(R.id.loweMeatButton);
        Button onlyFishButton = ecoView.findViewById(R.id.onlyFishButton);
        Button vegetarianButton = ecoView.findViewById(R.id.vegetarianButton);
        Button veganButton = ecoView.findViewById(R.id.veganButton);
        DietsCreator dietsCreator = new DietsCreator();

        //Test User Diet
        final Diet myDiet = new Diet();
        myDiet.setProteinPercent(ProteinSource.Beef, 0);
        myDiet.setProteinPercent(ProteinSource.Pork, 0);
        myDiet.setProteinPercent(ProteinSource.Chicken, 0);
        myDiet.setProteinPercent(ProteinSource.Fish, 0);
        myDiet.setProteinPercent(ProteinSource.Eggs, 0);
        myDiet.setProteinPercent(ProteinSource.Beans, 0);
        myDiet.setProteinPercent(ProteinSource.Vegetables, 0);

        if(UserDietInfo.getInstance().getTotalAmountOfProtein() > 0){
            int totalAmountOfProtein = UserDietInfo.getInstance().getTotalAmountOfProtein();
            myDiet.setProteinPercent(ProteinSource.Beef, UserDietInfo.getInstance().getAmountOfBeef()*100/totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Pork, UserDietInfo.getInstance().getAmountOfPork()*100/totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Chicken, UserDietInfo.getInstance().getAmountOfChicken()*100/totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Fish, UserDietInfo.getInstance().getAmountOfFish()*100/totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Eggs, UserDietInfo.getInstance().getAmountOfEgg()*100/totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Beans, UserDietInfo.getInstance().getAmountOfBean()*100/totalAmountOfProtein);
            myDiet.setProteinPercent(ProteinSource.Vegetables, UserDietInfo.getInstance().getAmountOfVegetable()*100/totalAmountOfProtein);
        }
        dietsCreator.setUserDiet(myDiet);

        //Default Diets in Eco Fragment
        final Diet highMeat = dietsCreator.createMeatEaterDiet();
        final Diet lowMeat = dietsCreator.createLowMeatDiet();
        final Diet onlyFish = dietsCreator.createOnlyFishDiet();
        final Diet vegetarian = dietsCreator.createVegetarianDiet();
        final Diet vegan = dietsCreator.createVeganDiet();
        final DietComparer dietComparer = new DietComparer();

        //textViews for showing better percentage in Econ Fragment
        highMeatBetterPercentageView
                .setText(dietComparer.compareCO2ePercent(myDiet, highMeat) + " %");
        lowMeatBetterPercentageView
                .setText(dietComparer.compareCO2ePercent(myDiet, lowMeat) + " %");
        onlyFishBetterPercentageView
                .setText(dietComparer.compareCO2ePercent(myDiet, onlyFish) + " %");
        vegetarianBetterPercentageView
                .setText(dietComparer.compareCO2ePercent(myDiet, vegetarian) + " %");
        veganBetterPercentageView.setText(dietComparer.compareCO2ePercent(myDiet, vegan) + " %");

        // Button listeners for Eco Fragment
        highMeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.highMeatButtonInfo, Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getActivity(), R.string.highMeatButtonChange, Toast.LENGTH_SHORT)
                        .show();
                if(UserDietInfo.getInstance().getTotalAmountOfProtein() > 0) dietTextView.setText(dietComparer.getChangeReport(myDiet, highMeat));
            }
        });

        lowMeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.lowMeatButtonInfo, Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getActivity(), R.string.lowMeatButtonChange, Toast.LENGTH_LONG)
                        .show();
                if(UserDietInfo.getInstance().getTotalAmountOfProtein() > 0) dietTextView.setText(dietComparer.getChangeReport(myDiet, lowMeat));
            }
        });
        onlyFishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.onlyFishButtonInfo, Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getActivity(), R.string.onlyFishButtonChange, Toast.LENGTH_SHORT)
                        .show();
                if(UserDietInfo.getInstance().getTotalAmountOfProtein() > 0) dietTextView.setText(dietComparer.getChangeReport(myDiet, onlyFish));
            }
        });
        vegetarianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.vegetarianButtonInfo, Toast.LENGTH_SHORT)
                        .show();
                Toast.makeText(getActivity(), R.string.vegetarianButtonChange, Toast.LENGTH_SHORT)
                        .show();
                if(UserDietInfo.getInstance().getTotalAmountOfProtein() > 0) dietTextView.setText(dietComparer.getChangeReport(myDiet, vegetarian));
            }
        });
        veganButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.veganButtonInfo, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), R.string.veganButtonChange, Toast.LENGTH_SHORT)
                        .show();
                if(UserDietInfo.getInstance().getTotalAmountOfProtein() > 0) dietTextView.setText(dietComparer.getChangeReport(myDiet, vegan));
            }
        });
        return ecoView;
    }
}
