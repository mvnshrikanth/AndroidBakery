package com.example.kaka.androidbakery.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Recipe;
import com.example.kaka.androidbakery.data.Step;

import static com.example.kaka.androidbakery.ui.StepFragment.STEP_DATA;

public class DetailActivity extends AppCompatActivity implements DetailStepFragment.Communicator {

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

    @Override
    public void respond(Step step) {
        Bundle bundle = new Bundle();
        DetailStepFragment detailStepFragment = new DetailStepFragment();
        bundle.putParcelable(STEP_DATA, step);
        detailStepFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_step_detail_container, detailStepFragment)
                .commit();
    }
}
