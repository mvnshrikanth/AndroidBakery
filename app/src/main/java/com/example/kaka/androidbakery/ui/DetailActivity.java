package com.example.kaka.androidbakery.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Recipe;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Recipe recipe;

        if (savedInstanceState == null) {
            recipe = getIntent().getParcelableExtra(MainFragment.RECIPE_DATA);
            Bundle bundle = new Bundle();
            DetailFragment detailFragment = new DetailFragment();
            bundle.putParcelable(MainFragment.RECIPE_DATA, recipe);
            detailFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fl_detail_container, detailFragment)
                    .commit();
        }
    }
}
