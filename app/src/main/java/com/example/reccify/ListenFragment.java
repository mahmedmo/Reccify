package com.example.reccify;

import static com.example.reccify.MainActivity.getMainActivity;
import static com.example.reccify.MainActivity.getMainContext;
import static com.example.reccify.MainActivity.hadToReconnect;
import static com.example.reccify.SpotifyApi.getConnected;
import static com.example.reccify.SpotifyApi.getDisconnected;
import static com.example.reccify.SpotifyApi.getSpotifyRemote;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

public class ListenFragment extends Fragment {
    public static AppCompatSeekBar mSeekBar;
    public static ListenFragment.TrackProgressBar mTrackProgressBar;
    static ImageView mCoverArtImageView;
    static Button mTrackView;
    static ImageView mPlay;
    static ImageView mPause;
    static ImageView mHeartEmpty;
    static ImageView mHeartFilled;
    static ImageView mArtist;
    static ImageView mLike;
    static ImageView mDislike;
    static Button mSaveButton;
    static Button mArtistButton;
    static Button mLikeButton;
    static Button mDislikeButton;

    public static class TrackProgressBar {

        private static final int LOOP_DURATION = 500;
        private final SeekBar mSeekBar;
        private final Handler mHandler;

        public static final SeekBar.OnSeekBarChangeListener mSeekBarChangeListener =
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        ;


                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        getSpotifyRemote()
                                .getPlayerApi()
                                .seekTo(seekBar.getProgress())
                                .setErrorCallback(null);


                    }
                };

        public final Runnable mSeekRunnable =
                new Runnable() {
                    @Override
                    public void run() {
                        int progress = mSeekBar.getProgress();
                        mSeekBar.setProgress(progress + LOOP_DURATION);
                        mHandler.postDelayed(mSeekRunnable, LOOP_DURATION);
                    }
                };

        public TrackProgressBar(SeekBar seekBar) {
            mSeekBar = seekBar;
            mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
            mHandler = new Handler();
        }

        public void setDuration(long duration) {

            mSeekBar.setMax((int) duration);
        }


        public void update(long progress) {
            mSeekBar.setProgress((int) progress);
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            getDisconnected();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //FRAGMENT SETUP BELOW

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ListenFragment() {

    }

    public static ListenFragment newInstance(String param1, String param2) {
        ListenFragment fragment = new ListenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        mSeekBar = view.findViewById(R.id.seek_to);
        mHeartEmpty = view.findViewById(R.id.heartEmpty);
        mHeartFilled = view.findViewById(R.id.heartFilled);
        mArtist = view.findViewById(R.id.artist);
        mLike = view.findViewById(R.id.like);
        mDislike = view.findViewById(R.id.dislike);
        mSaveButton = view.findViewById(R.id.saveButton);
        mArtistButton = view.findViewById(R.id.artistButton);
        mLikeButton = view.findViewById(R.id.likeButton);
        mDislikeButton = view.findViewById(R.id.dislikeButton);
        mSeekBar.setEnabled(false);
        mSeekBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mSeekBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        mTrackProgressBar = new ListenFragment.TrackProgressBar(mSeekBar);

        mCoverArtImageView = view.findViewById(R.id.songView);
        mTrackView = view.findViewById((R.id.trackName));
        mTrackView.setVisibility(View.INVISIBLE);
        mTrackView.setText("Loading");
        mPlay = view.findViewById(R.id.play);
        mPause = view.findViewById(R.id.pause);

        if (!getSpotifyRemote().isConnected()) {
            Toast.makeText(getMainContext(), ("Reconnecting..."), Toast.LENGTH_SHORT).show();
            getConnected(getMainContext(), getMainActivity());
            MainActivity.sp = getSpotifyRemote();
            Toast.makeText(getMainContext(), ("Connected!"), Toast.LENGTH_SHORT).show();
            hadToReconnect = true;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listen, container, false);
    }

}