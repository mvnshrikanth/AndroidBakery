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
import android.widget.Toast;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Step;
import com.example.kaka.androidbakery.utilities.StepsAdapter;
import com.example.kaka.androidbakery.utilities.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment implements StepsAdapter.StepAdapterOnClickHandler {

    public static final String STEPS_KEY = "steps";
    public static final String STEP_DATA = "step";
    @BindView(R.id.rv_steps_list)
    RecyclerView recyclerViewStepsList;
    private Unbinder unbinder;

    public StepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this, view);
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
    public void onClick(Step step) {
        if (Utils.isLargeScreen(getActivity())) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fl_step_detail_container, new DetailStepFragment())
                    .commit();
        } else {
            if (!step.getVideoURL().equals("")) {
                Intent intent = new Intent(getActivity(), DetailStepActivity.class);
                intent.putExtra(STEP_DATA, step);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "No video for this step.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
