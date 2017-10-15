package com.onval.bakingapp.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.onval.bakingapp.R;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.utils.FormatUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.onval.bakingapp.view.StepDetailFragment.STEP_LIST_TAG;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_POSITION_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements IDetailView.Listener {

    ArrayList<Step> stepList;
    int stepPosition;

    @BindView(R.id.exoplayer_view) SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.step_title) TextView title;
    @BindView(R.id.step_instruction) TextView instruction;

    @BindView(R.id.btn_previous) Button previous;
    @BindView(R.id.btn_next) Button next;

    private SimpleExoPlayer player;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(ArrayList<Step> steplist, int stepPosition) {
        DetailFragment fragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEP_LIST_TAG, steplist);
        bundle.putInt(STEP_POSITION_TAG, stepPosition);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, root);

        //Get current step from fragment arguments
        Bundle args = getArguments();
        stepList = args.getParcelableArrayList(STEP_LIST_TAG);
        stepPosition = args.getInt(STEP_POSITION_TAG);
        Step step = stepList.get(stepPosition);

        //hide previous and next buttons if on a tablet
        if (getResources().getBoolean(R.bool.isTablet)) {
            previous.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        }

        //disable prev and next buttons on edges
        //as extra control and ux improvement
        if (stepPosition == 0)
            previous.setVisibility(View.GONE);
        if (stepPosition == stepList.size()-1)
            next.setVisibility(View.GONE);

        //set the step description
        title.setText(step.getShortDescription());
        instruction.setText(FormatUtils.formatStepInstructions(step.getDescription()));

        //set up the video player
        Uri uri = Uri.parse(step.getVideoURL());

        if (uri.toString().equals(""))
            uri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");

        MediaSource source = new ExtractorMediaSource(
                    uri,
                    new DefaultDataSourceFactory(getContext(),
                            Util.getUserAgent(getContext(), getString(R.string.recipe_list_title))),
                    new DefaultExtractorsFactory(),
                    null,
                    null
            );

        player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());

        player.prepare(source);
        //todo: after playing switch automatically to the next step
        player.setPlayWhenReady(true);

        exoPlayerView.setPlayer(player);

        return root;
    }

    //todo: also set some sort of deactivate method for buttons
    //todo: I'M NOT LIKING THIS (BESIDES IT'S NOT WORKING CORRECTLY)
    @Override
    @OnClick(R.id.btn_previous)
    public void onPreviousClicked() {
        if (stepPosition > 0) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(STEP_LIST_TAG, stepList);
            intent.putExtra(STEP_POSITION_TAG, stepPosition - 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    @Override
    @OnClick(R.id.btn_next)
    public void onNextClicked() {
        if (stepPosition < stepList.size()-1) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(STEP_LIST_TAG, stepList);
            intent.putExtra(STEP_POSITION_TAG, stepPosition + 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        player.setPlayWhenReady(false);
        player.release();
    }
}
