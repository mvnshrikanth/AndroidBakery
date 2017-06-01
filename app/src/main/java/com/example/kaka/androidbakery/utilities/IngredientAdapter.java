package com.example.kaka.androidbakery.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kaka on 5/19/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private static final String LOG_TAG = IngredientAdapter.class.getSimpleName();
    private final List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);

        String stringIngredientName = (position + 1) + ". " + ingredient.getIngredient().substring(0, 1).toUpperCase()
                + ingredient.getIngredient().substring(1);
        holder.textViewIngredient.setText(stringIngredientName);
        String measure = ingredient.getQuantity() + " " + ingredient.getMeasure();
        holder.textViewMeasure.setText(measure);
    }

    @Override
    public int getItemCount() {
        if (null == ingredientList) return 0;
        return ingredientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingredient)
        TextView textViewIngredient;
        @BindView(R.id.tv_measure)
        TextView textViewMeasure;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
