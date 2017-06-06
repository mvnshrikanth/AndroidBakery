package com.example.kaka.androidbakery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();
    private final RecipeAdapterOnClickHandler recipeAdapterOnClickHandler;
    private List<Recipe> recipeList;

    public RecipeAdapter(RecipeAdapterOnClickHandler recipeAdapterOnClickHandler) {
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

        switch (recipe.getName()) {
            case "Nutella Pie":
                holder.imageViewRecipe.setImageResource(R.drawable.nutella_pie);
                break;
            case "Brownies":
                holder.imageViewRecipe.setImageResource(R.drawable.brownie);
                break;
            case "Yellow Cake":
                holder.imageViewRecipe.setImageResource(R.drawable.yellow_cake);
                break;
            case "Cheesecake":
                holder.imageViewRecipe.setImageResource(R.drawable.cheesecake);
                break;
            default:
                holder.imageViewRecipe.setImageResource(R.drawable.no_image_found);
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
            recipeAdapterOnClickHandler.onClick(recipeList.get(getAdapterPosition()));
        }
    }
}
