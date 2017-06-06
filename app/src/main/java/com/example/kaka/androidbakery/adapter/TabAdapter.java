package com.example.kaka.androidbakery.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentPagerAdapter;

import com.example.kaka.androidbakery.data.Ingredient;
import com.example.kaka.androidbakery.data.Step;
import com.example.kaka.androidbakery.ui.IngredientFragment;
import com.example.kaka.androidbakery.ui.StepFragment;

import java.util.ArrayList;
import java.util.List;


public class TabAdapter extends FragmentPagerAdapter {

    private final List<Ingredient> ingredientList;
    private final List<Step> stepList;

    public TabAdapter(FragmentManager fm, List<Ingredient> ingredientList, List<Step> stepList) {
        super(fm);
        this.ingredientList = ingredientList;
        this.stepList = stepList;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();

        if (position == 0) {
            IngredientFragment ingredientFragment = new IngredientFragment();
            bundle.putParcelableArrayList(IngredientFragment.INGREDIENT_KEY, (ArrayList<? extends Parcelable>) ingredientList);
            ingredientFragment.setArguments(bundle);
            return ingredientFragment;
        } else {
            StepFragment stepFragment = new StepFragment();
            bundle.putParcelableArrayList(StepFragment.STEPS_KEY, (ArrayList<? extends Parcelable>) stepList);
            stepFragment.setArguments(bundle);
            return stepFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Ingredient";
        } else {
            return "Steps";
        }
    }
}
