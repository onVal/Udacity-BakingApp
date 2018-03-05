package com.onval.bakingapp.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.onval.bakingapp.R;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.utils.FormatUtils;
import com.squareup.picasso.Picasso;

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
    @BindView(R.id.thumbnail_img) ImageView thumbnailView;

    @BindView(R.id.step_title) TextView title;
    @BindView(R.id.step_instruction) TextView instruction;

    @BindView(R.id.btn_previous) Button previous;
    @BindView(R.id.btn_next) Button next;

    private SimpleExoPlayer player;
    private Uri videoUri;

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

        //disable prev and next buttons if not needed
        //as extra control and ux improvement
        if (stepPosition == 0)
            previous.setVisibility(View.GONE);
        if (stepPosition == stepList.size()-1)
            next.setVisibility(View.GONE);

        //set the step description
        title.setText(step.getShortDescription());
        instruction.setText(FormatUtils.formatStepInstructions(step.getDescription()));

        //retrieve video uri
        videoUri = Uri.parse(step.getVideoURL());

        //since the data i'm retrieving from is kind of messed up, sometimes thumbnailView
        //actually contains the video url.
        //if that's the case, put the url in the videoUri variable so that it gets loaded properly
        //The solution is a bit spartan, but for the purposes of this project it should work
        if (step.getThumbnailURL().endsWith("mp4"))
            videoUri = Uri.parse(step.getThumbnailURL());

        if (videoUri.toString().equals("")) { //if there's no video uri
            exoPlayerView.setVisibility(View.INVISIBLE);
            thumbnailView.setVisibility(View.VISIBLE);

            String stepImageUrl = step.getThumbnailURL();

            if (!stepImageUrl.equals("")) //if there's an image url
                Picasso.with(getContext())
                        .load(stepImageUrl)
                        .into(thumbnailView);
            else //otherwise load a default 'no media message'
                thumbnailView.setImageResource(R.drawable.no_media);
        }
        else { // if there is a video uri...
            Log.d("DERPO", videoUri.toString());
            initializePlayer(videoUri);
        }

        return root;
    }

    private void initializePlayer(Uri uri) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        exoPlayerView.setPlayer(player);

        //prepare the MediaSource
        String userAgent = Util.getUserAgent(getContext(), getString(R.string.recipe_list_title));

        MediaSource mediaSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(),
                null,
                null
        );

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.release();
            player = null;
        }
    }

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
        releasePlayer();
    }
}
