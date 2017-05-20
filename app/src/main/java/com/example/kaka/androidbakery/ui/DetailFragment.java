package com.example.kaka.androidbakery.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Ingredient;
import com.example.kaka.androidbakery.data.Recipe;
import com.example.kaka.androidbakery.data.Step;
import com.example.kaka.androidbakery.utilities.TabAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Recipe recipe = this.getArguments().getParcelable(MainFragment.RECIPE_DATA);
        ButterKnife.bind(this, view);

        List<Ingredient> ingredientList = recipe.getIngredients();
        List<Step> stepList = recipe.getSteps();

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager(), ingredientList, stepList);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
