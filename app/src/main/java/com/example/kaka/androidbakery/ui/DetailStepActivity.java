package com.example.kaka.androidbakery.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kaka.androidbakery.R;

public class DetailStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        getFragmentManager().beginTransaction()
                .replace(R.id.fl_step_detail_container, new DetailStepFragment())
                .commit();
    }
}
