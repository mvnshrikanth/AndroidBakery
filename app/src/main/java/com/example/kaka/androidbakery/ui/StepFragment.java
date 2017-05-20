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
import com.example.kaka.androidbakery.data.Step;
import com.example.kaka.androidbakery.utilities.StepsAdapter;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    public static final String STEPS_KEY = "steps";
    @BindView(R.id.rv_steps_list)
    RecyclerView recyclerViewStepsList;
    private List<Step> stepList;

    public StepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);

        savedInstanceState = this.getArguments();
        stepList = savedInstanceState.getParcelableArrayList(STEPS_KEY);
        StepsAdapter stepsAdapter = new StepsAdapter(stepList);

        recyclerViewStepsList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewStepsList.setItemAnimator(new DefaultItemAnimator());
        recyclerViewStepsList.setHasFixedSize(true);
        recyclerViewStepsList.setAdapter(stepsAdapter);

        return view;
    }

}
