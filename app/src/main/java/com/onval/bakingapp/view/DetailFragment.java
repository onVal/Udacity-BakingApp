package com.onval.bakingapp.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.onval.bakingapp.adapter.StepAdapter;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.utils.FormatUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.onval.bakingapp.view.StepDetailFragment.STEP_ID_TAG;
import static com.onval.bakingapp.view.StepDetailFragment.STEP_INSTRUCTION_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements IDetailView.Listener {

    ArrayList<Step> stepList;
    int stepId;

    @BindView(R.id.exoplayer_view) SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.step_title) TextView title;
    @BindView(R.id.step_instruction) TextView instruction;

    private SimpleExoPlayer player;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, root);

        //Get current step from intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        stepList = extras.getParcelableArrayList(STEP_INSTRUCTION_TAG);
        stepId = extras.getInt(STEP_ID_TAG);
        Step step = StepAdapter.findStepById(stepList, stepId);

        title.setText(step.getShortDescription());
        instruction.setText(FormatUtils.formatStepInstructions(step.getDescription()));

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
    //todo: I'M NOT LIKING THIS (BESIDES IT'S NOT WORKING CORRECTLY): CHECK OUT SINGLETONS FRAGMENTS
    @Override
    @OnClick(R.id.btn_previous)
    public void onPreviousClicked() {
        if (stepId > 0) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(STEP_INSTRUCTION_TAG, stepList);
            intent.putExtra(STEP_ID_TAG, stepId - 1); //todo: this is bad, but should work regardless
            startActivity(intent);
        }
    }

    @Override
    @OnClick(R.id.btn_next)
    public void onNextClicked() {
        if (stepId < stepList.size()) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(STEP_INSTRUCTION_TAG, stepList);
            intent.putExtra(STEP_ID_TAG, stepId + 1); //todo: this is bad, but should work regardless
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
