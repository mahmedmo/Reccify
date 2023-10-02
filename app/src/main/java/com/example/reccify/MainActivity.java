package com.example.reccify;

import static com.example.reccify.CurrentUserFile.getCurrentUser;
import static com.example.reccify.CurrentUserFile.removeCurrentUser;
import static com.example.reccify.GenreList.genreList;
import static com.example.reccify.GenreList.getRandomGenre;
import static com.example.reccify.ListenFragment.mTrackView;
import static com.example.reccify.SongFinder.dislike;
import static com.example.reccify.SongFinder.like;
import static com.example.reccify.SongFinder.playlist;
import static com.example.reccify.ListenFragment.mArtist;
import static com.example.reccify.ListenFragment.mArtistButton;
import static com.example.reccify.ListenFragment.mDislike;
import static com.example.reccify.ListenFragment.mDislikeButton;
import static com.example.reccify.ListenFragment.mHeartEmpty;
import static com.example.reccify.ListenFragment.mHeartFilled;
import static com.example.reccify.ListenFragment.mLike;
import static com.example.reccify.ListenFragment.mLikeButton;
import static com.example.reccify.ListenFragment.mPause;
import static com.example.reccify.ListenFragment.mPlay;

import static com.example.reccify.ListenFragment.mSaveButton;
import static com.example.reccify.ListenFragment.mSeekBar;
import static com.example.reccify.SpotifyApi.getConnected;
import static com.example.reccify.SpotifyApi.getDisconnected;
import static com.example.reccify.SpotifyApi.getSpotifyRemote;
import static com.example.reccify.SpotifyApi.onSubscribedToPlayerStateButtonClicked;
import static com.example.reccify.SpotifyApi.setActivityChanged;
import static com.example.reccify.SpotifyApi.spotifyLogin;
import static com.example.reccify.SpotifyApi.stopPlayback;
import static com.example.reccify.SpotifyApi.track;
import static com.example.reccify.UserFile.readFromFile;
import static com.example.reccify.UserPreferencesActivity.savePreferencesToFile;

import com.example.reccify.databinding.ActivityMainBinding;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Toast;

import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.PlayerState;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    public static Activity activity;
    public static PlayerState playerstate;
    static SpotifyAppRemote sp = getSpotifyRemote();
    public static boolean firstTime = true;
    public static ArrayList<String> trackHistory = new ArrayList<String>();
    public static ArrayList<String> likedArtists = new ArrayList<String>(0);
    public static ArrayList<String> likedSongs = new ArrayList<String>(0);
    public static boolean hadToReconnect = false;

    public void playback(View view) throws InterruptedException {


        if (mPlay.getVisibility() == View.INVISIBLE) {
            sp.getPlayerApi().pause();
            mPause.setVisibility(View.INVISIBLE);
            mPlay.setVisibility(View.VISIBLE);

        } else if (mPlay.getVisibility() == View.VISIBLE) {

            if (firstTime || hadToReconnect) {
                mTrackView.setText("Loading...");
                mLike.setVisibility(View.VISIBLE);
                mDislike.setVisibility(View.VISIBLE);
                mArtist.setVisibility(View.VISIBLE);
                mHeartEmpty.setVisibility(View.VISIBLE);
                mSaveButton.setVisibility(View.VISIBLE);
                mArtistButton.setVisibility(View.VISIBLE);
                mLikeButton.setVisibility(View.VISIBLE);
                mDislikeButton.setVisibility(View.VISIBLE);
                mSeekBar.setVisibility(View.VISIBLE);
                sp.getPlayerApi().setShuffle(true);
                sp.getPlayerApi().play(playlist(getRandomGenre()));
                for (String t : trackHistory) {
                    if (track.name.equals(t)) {
                        sp.getPlayerApi().play(playlist(getRandomGenre()));
                    }
                }

                mPlay.setVisibility(View.INVISIBLE);
                mPause.setVisibility(View.VISIBLE);

                Thread.sleep(1000);
                hadToReconnect = false;
                firstTime = false;
                onSubscribedToPlayerStateButtonClicked(null);
                mTrackView.setVisibility(View.VISIBLE);

            } else {
                mPlay.setVisibility(View.INVISIBLE);
                ;
                mPause.setVisibility(View.VISIBLE);
                sp.getPlayerApi().resume();
            }


        }
    }

    public void saveTrack(View view) {
        boolean alreadySaved = false;
        sp.getUserApi().addToLibrary(track.uri);
        String likedSong = track.name + " -" + track.artist.name;
        likedSong.replaceAll(",", " ");
        likedSong.replaceAll(";", " ");
        String fileContent = readFromFile(getCurrentUser().getFileName());
        for (String s : likedSongs) {
            if (s.equals(likedSong)) {
                alreadySaved = true;
            }
        }
        if (!alreadySaved) {
            mHeartFilled.setVisibility(View.VISIBLE);
            int index = fileContent.indexOf("*");
            int index2 = fileContent.indexOf(" Prefferred Genres:");
            likedSongs.add(likedSong);
            String str = fileContent.substring(0, index);
            String str2 = "";
            for (String s : likedSongs) {
                str2 = str2 + " *" + s + "*";
            }
            String str3 = fileContent.substring(index2);

            String finalStr = str + str2 + str3;
            getCurrentUser().setFileText(getCurrentUser().getFileName(), finalStr);
            String fileContent2 = readFromFile(getCurrentUser().getFileName());
            Log.d("File", readFromFile(getCurrentUser().getFileName()));
            Log.d("VERIFY", fileContent2);
        } else {

            Toast.makeText(getMainContext(), ("Already saved."), Toast.LENGTH_SHORT).show();
            Log.d("File", readFromFile(getCurrentUser().getFileName()));
        }


    }

    public void playArtist(View view) {
        boolean alreadySaved = false;
        String artistUri = track.artist.uri;
        sp.getPlayerApi().play(artistUri);
        String fileContent = readFromFile(getCurrentUser().getFileName());
        int index = fileContent.indexOf(";");
        int index2 = fileContent.indexOf(" Saved Songs:");
        String likedArtistStr = "";
        String str3 = "";
        for (String s : likedArtists) {
            if (s.equals(artistUri)) {
                alreadySaved = true;
            }
        }
        if (!alreadySaved) {
            if (likedArtists.size() >= 1) {
                for (String s : likedArtists) {
                    likedArtistStr = likedArtistStr + s;
                }

            }
            likedArtists.add(artistUri);

            String str = fileContent.substring(0, index);
            String str2 = "";
            for (String s : likedArtists) {
                str2 = str2 + " ;" + s + ";";
            }

            str3 = fileContent.substring(index2);
            String finalStr = str + str2 + str3;
            getCurrentUser().setFileText(getCurrentUser().getFileName(), finalStr);
            Log.d("File", readFromFile(getCurrentUser().getFileName()));
        }
        mHeartFilled.setVisibility(View.INVISIBLE);
        onSubscribedToPlayerStateButtonClicked(null);
    }

    public void likeRequest(View view) {

        like();
        savePreferencesToFile();
        sp.getPlayerApi().setShuffle(true);


        sp.getPlayerApi().play(playlist(getRandomGenre()));

        for (String t : trackHistory) {
            if (track.name.equals(t)) {
                sp.getPlayerApi().play(playlist(getRandomGenre()));
            }
        }
        mHeartFilled.setVisibility(View.INVISIBLE);
        mPlay.setVisibility(View.INVISIBLE);
        ;
        mPause.setVisibility(View.VISIBLE);
        onSubscribedToPlayerStateButtonClicked(null);

    }

    public void dislikeRequest(View view) {
        dislike();
        savePreferencesToFile();
        sp.getPlayerApi().setShuffle(true);


        sp.getPlayerApi().play(playlist(getRandomGenre()));

        for (String t : trackHistory) {
            if (track.name.equals(t)) {
                sp.getPlayerApi().play(playlist(getRandomGenre()));
            }
        }
        mHeartFilled.setVisibility(View.INVISIBLE);
        mPlay.setVisibility(View.INVISIBLE);
        ;
        mPause.setVisibility(View.VISIBLE);
        onSubscribedToPlayerStateButtonClicked(null);
    }

    public void help(View view) {
        Intent accountLogin = new Intent(this, HelpActivity.class);
        startActivity(accountLogin);
    }

    public void signOut(View view) throws InterruptedException {
        mLike.setVisibility(View.INVISIBLE);
        mDislike.setVisibility(View.INVISIBLE);
        mArtist.setVisibility(View.INVISIBLE);
        mHeartEmpty.setVisibility(View.INVISIBLE);
        mSaveButton.setVisibility(View.INVISIBLE);
        mArtistButton.setVisibility(View.INVISIBLE);
        mLikeButton.setVisibility(View.INVISIBLE);
        mDislikeButton.setVisibility(View.INVISIBLE);
        mSeekBar.setVisibility(View.INVISIBLE);
        stopPlayback();
        removeCurrentUser();
        genreList.removeAll(genreList);
        trackHistory.removeAll(trackHistory);
        likedArtists.removeAll(likedArtists);
        likedSongs.removeAll(likedSongs);
        firstTime = true;
        setActivityChanged(false);
        spotifyLogin(getApplicationContext(), this);
        Intent accountLogin = new Intent(this, AccountActivity.class);
        startActivity(accountLogin);
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

    public void resetPreferences(View view) {
        genreList.removeAll(genreList);

        Intent userPreferences = new Intent(this, UserPreferencesActivity.class);
        startActivity(userPreferences);

    }

    public static Context getMainContext() {
        return context;
    }

    public static Activity getMainActivity() {
        return activity;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentView, fragment);
        fragmentTransaction.commit();
    }


    //FULLSCREEN ACTIVITY SETUP BELOW

    @Override
    public void onBackPressed() {

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(hideSystemBars());


        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }

    private static final boolean AUTO_HIDE = true;


    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;


    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private View mControlsView;
    private boolean mVisible;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener((new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        }));
        replaceFragment(new ListenFragment());
        binding.navView.setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.firstFragment:
                            replaceFragment((new SavedSongsFragment()));
                            break;
                        case R.id.secondFragment:
                            replaceFragment((new ListenFragment()));
                            break;
                        case R.id.thirdFragment:
                            replaceFragment((new AccountSettingsFragment()));
                            break;

                    }
                    return true;
                }

        );

        activity = this;
        context = getApplicationContext();
        mVisible = true;
        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.fullscreenContentControls;
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;


        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {

                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    };

    private void show() {

        if (Build.VERSION.SDK_INT >= 30) {
            mContentView.getWindowInsetsController().show(
                    WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        } else {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
        mVisible = true;


        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };


    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}