package com.example.kaka.androidbakery.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kaka.androidbakery.R;

import static com.example.kaka.androidbakery.ui.StepFragment.STEP_DATA;

public class DetailStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);
        Bundle bundle = new Bundle();
        DetailStepFragment detailStepFragment = new DetailStepFragment();

        bundle.putParcelable(STEP_DATA, getIntent().getParcelableExtra(STEP_DATA));
        detailStepFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(R.id.fl_step_detail_container, detailStepFragment)
                .commit();
    }

}
