package com.example.kaka.androidbakery.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.adapter.TabAdapter;
import com.example.kaka.androidbakery.data.Ingredient;
import com.example.kaka.androidbakery.data.Recipe;
import com.example.kaka.androidbakery.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    private Unbinder unbinder;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        savedInstanceState = this.getArguments();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        Recipe recipe = savedInstanceState.getParcelable(MainFragment.RECIPE_DATA);
        unbinder = ButterKnife.bind(this, view);

        List<Ingredient> ingredientList = recipe != null ? recipe.getIngredients() : null;
        List<Step> stepList = recipe != null ? recipe.getSteps() : null;

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager(), ingredientList, stepList);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
