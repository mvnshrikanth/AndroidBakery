package com.example.kaka.androidbakery.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kaka on 5/19/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    private static final String LOG_TAG = StepsAdapter.class.getSimpleName();
    private final StepAdapterOnClickHandler stepAdapterOnClickHandler;
    private final List<Step> stepList;

    public StepsAdapter(List<Step> stepList, StepAdapterOnClickHandler stepAdapterOnClickHandler) {
        this.stepList = stepList;
        this.stepAdapterOnClickHandler = stepAdapterOnClickHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Step step = stepList.get(position);
        holder.textViewStep.setText((position + 1) + ". " + step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == stepList) return 0;
        return stepList.size();
    }

    public interface StepAdapterOnClickHandler {
        void onClick(Step step);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step)
        TextView textViewStep;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            stepAdapterOnClickHandler.onClick(stepList.get(getAdapterPosition()));
        }
    }
}
