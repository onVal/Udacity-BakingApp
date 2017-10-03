package com.onval.bakingapp.view;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.onval.bakingapp.data.Step;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer player;
    TextView instruction;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        exoPlayerView = (SimpleExoPlayerView) root.findViewById(R.id.exoplayer_view);
        instruction = (TextView) root.findViewById(R.id.step_instruction);

        Step step = getActivity().getIntent().getExtras().getParcelable(StepDetailFragment.STEP_INSTRUCTION_TAG);

        instruction.setText(step.getShortDescription() + "\n\n" + step.getDescription());

        Uri uri = Uri.parse(step.getVideoURL());

        if (uri.toString().equals(""))
            uri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");

        Log.d("URI", uri.toString());

        MediaSource source = new ExtractorMediaSource(
                    uri,
                    new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingRecipes")),
                    new DefaultExtractorsFactory(),
                    null,
                    null
            );

        player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());

        player.prepare(source);
        player.setPlayWhenReady(true);

        exoPlayerView.setPlayer(player);

        return root;
    }

    //todo: set ondestroy too and fix bugs related to video playback
}
