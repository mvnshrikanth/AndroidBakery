package com.example.kaka.androidbakery.ui;


import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Step;
import com.example.kaka.androidbakery.utilities.Utils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.kaka.androidbakery.ui.StepFragment.STEP_DATA;
import static com.example.kaka.androidbakery.ui.StepFragment.STEP_LIST_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStepFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.iv_no_video_found)
    ImageView imageViewNoVideo;
    @BindView(R.id.exo_video)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.tv_recipe_step_instruction)
    TextView textViewStepInstructions;
    @BindView(R.id.bt_previous_step)
    Button buttonPreviousStep;
    @BindView(R.id.bt_next_step)
    Button buttonNextStep;

    private Unbinder unbinder;

    private SimpleExoPlayer simpleExoPlayer;
    private Step step;
    private List<Step> stepList;

    public DetailStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_step, container, false);
        unbinder = ButterKnife.bind(this, view);

        savedInstanceState = this.getArguments();
        step = savedInstanceState.getParcelable(STEP_DATA);
        stepList = savedInstanceState.getParcelableArrayList(STEP_LIST_DATA);
        String videoUrl = step.getVideoURL();
        if (!videoUrl.equals("")) {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            imageViewNoVideo.setVisibility(View.GONE);
            if (simpleExoPlayer == null) {
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(view.getContext(), trackSelector, loadControl);
                simpleExoPlayerView.setPlayer(simpleExoPlayer);
                String userAgent = Util.getUserAgent(view.getContext(), "AndroidBakery");
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                        new DefaultDataSourceFactory(view.getContext(), userAgent),
                        new DefaultExtractorsFactory(), null, null);
                simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.setPlayWhenReady(true);
            }
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
            imageViewNoVideo.setVisibility(View.VISIBLE);
            imageViewNoVideo.setImageResource(R.drawable.no_image_found);
        }
        textViewStepInstructions.setText(step.getDescription());
        if (Utils.isLargeScreen(view.getContext())) {
            buttonPreviousStep.setVisibility(View.GONE);
            buttonNextStep.setVisibility(View.GONE);
        } else {
            buttonNextStep.setOnClickListener(this);
            buttonPreviousStep.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        int currIndexOfStepList, nexIndexOfStepList, prevIndexOfStepList;
        currIndexOfStepList = step.getId();
        prevIndexOfStepList = currIndexOfStepList - 1;
        nexIndexOfStepList = currIndexOfStepList + 1;

        Step bundleStep;
        boolean booleanReplace;

        Bundle bundle = new Bundle();
        DetailStepFragment detailStepFragment = new DetailStepFragment();

        switch (v.getId()) {
            case R.id.bt_next_step:
                if (nexIndexOfStepList >= stepList.size()) {
                    bundleStep = step;
                    booleanReplace = false;
                    Toast.makeText(getActivity().getApplicationContext(), "This was the last step.", Toast.LENGTH_SHORT).show();
                } else {
                    booleanReplace = true;
                    bundleStep = stepList.get(nexIndexOfStepList);
                }
                break;
            default:
                if (prevIndexOfStepList < 0) {
                    bundleStep = step;
                    booleanReplace = false;
                    Toast.makeText(getActivity().getApplicationContext(), "This is the first step cant go back.", Toast.LENGTH_SHORT).show();
                } else {
                    booleanReplace = true;
                    bundleStep = stepList.get(prevIndexOfStepList);
                }
        }

        if (booleanReplace) {
            bundle.putParcelable(STEP_DATA, bundleStep);
            bundle.putParcelableArrayList(STEP_LIST_DATA, (ArrayList<? extends Parcelable>) stepList);
            detailStepFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fl_step_detail_container, detailStepFragment)
                    .commit();
        }
    }

    interface Communicator {
        void respond(Step step, List<Step> stepList);
    }
}
