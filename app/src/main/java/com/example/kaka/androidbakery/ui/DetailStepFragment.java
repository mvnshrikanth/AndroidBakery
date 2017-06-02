package com.example.kaka.androidbakery.ui;


import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaka.androidbakery.R;
import com.example.kaka.androidbakery.data.Step;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.kaka.androidbakery.ui.StepFragment.STEP_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStepFragment extends Fragment {

    @BindView(R.id.exo_video)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.tv_recipe_step_instruction)
    TextView textViewStepInstructions;
    @BindView(R.id.iv_no_video_found)
    ImageView imageViewNoVideo;
    @BindView(R.id.bt_previous_step)
    Button buttonPreviousStep;
    @BindView(R.id.bt_next_step)
    Button buttonNextStep;

    private Unbinder unbinder;

    private View mView;

    private SimpleExoPlayer simpleExoPlayer;

    public DetailStepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_step, container, false);
        mView = view;
        unbinder = ButterKnife.bind(this, view);

        savedInstanceState = this.getArguments();
        Step step = savedInstanceState.getParcelable(STEP_DATA);
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
            Picasso.with(getActivity().getApplicationContext())
                    .load(R.drawable.no_image_found)
                    .into(imageViewNoVideo);
        }
        textViewStepInstructions.setText(step.getDescription());
        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapFragments(v);
            }
        });
        buttonPreviousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapFragments(v);
            }
        });
        return view;
    }

    private void swapFragments(View v) {
        String strViewName = null;
        switch (v.getId()) {
            case R.id.bt_next_step:
                strViewName = "Next Step";
                break;
            case R.id.bt_previous_step:
                strViewName = "Previous Step";
                break;
            default:
                strViewName = "Sunny";
        }
        Toast.makeText(getActivity().getApplicationContext(), strViewName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

}
