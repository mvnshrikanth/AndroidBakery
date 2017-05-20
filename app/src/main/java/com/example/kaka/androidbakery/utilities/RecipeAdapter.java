package com.example.kaka.androidbakery.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kaka on 5/13/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();
    private final RecipeAdapterOnClickHandler recipeAdapterOnClickHandler;
    private List<Recipe> recipeList;

    private Context context;

    public RecipeAdapter(Context context, RecipeAdapterOnClickHandler recipeAdapterOnClickHandler) {
        this.context = context;
        recipeList = new ArrayList<>();
        this.recipeAdapterOnClickHandler = recipeAdapterOnClickHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        if (recipe.getName().equals("Nutella Pie")) {
            Picasso.with(context)
                    .load(R.drawable.nutella_pie)
                    .into(holder.imageViewRecipe);
        } else if (recipe.getName().equals("Brownies")) {
            Picasso.with(context)
                    .load(R.drawable.brownie)
                    .into(holder.imageViewRecipe);
        } else if (recipe.getName().equals("Yellow Cake")) {
            Picasso.with(context)
                    .load(R.drawable.yellow_cake)
                    .into(holder.imageViewRecipe);
        } else {
            Picasso.with(context)
                    .load(R.drawable.cheesecake)
                    .into(holder.imageViewRecipe);
        }
        holder.textViewRecipeName.setText(recipe.getName());

    }

    @Override
    public int getItemCount() {
        if (null == recipeList) return 0;
        return recipeList.size();
    }

    public void setRecipeData(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_recipe_image)
        ImageView imageViewRecipe;

        @BindView(R.id.tv_recipe_name)
        TextView textViewRecipeName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = recipeList.get(getAdapterPosition());
            recipeAdapterOnClickHandler.onClick(recipe);
        }
    }
}
