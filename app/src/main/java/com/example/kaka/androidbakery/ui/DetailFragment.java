package com.example.kaka.androidbakery.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Ingredient;
import com.example.kaka.androidbakery.data.Recipe;
import com.example.kaka.androidbakery.data.Step;
import com.example.kaka.androidbakery.utilities.IngredientAdapter;
import com.example.kaka.androidbakery.utilities.StepsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    @BindView(R.id.rv_ingredients_list)
    RecyclerView recyclerViewIngredientList;
    @BindView(R.id.rv_steps_list)
    RecyclerView recyclerViewStepsList;


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

        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientList);
        StepsAdapter stepsAdapter = new StepsAdapter(stepList);

        recyclerViewIngredientList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewIngredientList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewIngredientList.setHasFixedSize(true);
        recyclerViewIngredientList.setAdapter(ingredientAdapter);


        recyclerViewStepsList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewStepsList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewStepsList.setHasFixedSize(true);
        recyclerViewStepsList.setAdapter(stepsAdapter);

        return view;
    }

}
