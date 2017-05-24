package com.example.kaka.androidbakery.ui;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Step;
import com.example.kaka.androidbakery.utilities.StepsAdapter;
import com.example.kaka.androidbakery.utilities.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment implements StepsAdapter.StepAdapterOnClickHandler {

    public static final String STEPS_KEY = "steps";
    @BindView(R.id.rv_steps_list)
    RecyclerView recyclerViewStepsList;

    public StepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);
        savedInstanceState = this.getArguments();
        List<Step> stepList = savedInstanceState.getParcelableArrayList(STEPS_KEY);
        StepsAdapter stepsAdapter = new StepsAdapter(stepList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewStepsList.setLayoutManager(linearLayoutManager);
        recyclerViewStepsList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewStepsList.setHasFixedSize(true);
        recyclerViewStepsList.setAdapter(stepsAdapter);

        return view;
    }

    @Override
    public void onClick() {
        if (Utils.isLargeScreen(getActivity())) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fl_step_detail_container, new DetailStepFragment())
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailStepActivity.class);
            startActivity(intent);
        }
    }
}
