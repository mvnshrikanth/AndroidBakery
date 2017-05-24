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
import com.example.kaka.androidbakery.utilities.IngredientAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientFragment extends Fragment {

    public static final String INGREDIENT_KEY = "ingredient";
    @BindView(R.id.rv_ingredients_list)
    RecyclerView recyclerViewIngredientList;

    public IngredientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, view);
        savedInstanceState = this.getArguments();
        List<Ingredient> ingredientList = savedInstanceState.getParcelableArrayList(INGREDIENT_KEY);
        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewIngredientList.setLayoutManager(linearLayoutManager);
        recyclerViewIngredientList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewIngredientList.setHasFixedSize(true);
        recyclerViewIngredientList.setAdapter(ingredientAdapter);

        return view;
    }

}
