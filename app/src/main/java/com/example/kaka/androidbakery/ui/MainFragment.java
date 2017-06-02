package com.example.kaka.androidbakery.ui;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Ingredient;
import com.example.kaka.androidbakery.data.Recipe;
import com.example.kaka.androidbakery.data.Step;
import com.example.kaka.androidbakery.utilities.RecipeAdapter;
import com.example.kaka.androidbakery.utilities.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickHandler {

    public static final String RECIPE_DATA = "recipe";
    public static final String BAKERY_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String LOG_TAG = MainFragment.class.getSimpleName();
    @BindView(R.id.rv_recipe_list)
    RecyclerView recyclerView;
    @BindView(R.id.rl_empty_view_layout)
    View emptyView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;
    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private Unbinder unbinder;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        recipeList = new ArrayList<>();

        recipeAdapter = new RecipeAdapter(view.getContext(), this);

        int numberOfCol = 1;
        if (Utils.isLargeScreen(view.getContext())) {
            numberOfCol = 4;
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), numberOfCol);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeAdapter);

        loadRecipeData();

        return view;
    }

    @Override
    public void onClick(Recipe recipe) {

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(RECIPE_DATA, recipe);
        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loadRecipeData() {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder builder = HttpUrl.parse(BAKERY_BASE_URL).newBuilder();

        String url = builder.toString();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseData = response.body().string();

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {

                    try {
                        recipeList = Utils.getRecipeListFromJsonString(responseData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (recipeList.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recipeAdapter.setRecipeData(recipeList);
                        }
                    }
                });
            }
        });
    }
}
