package com.example.reccify;

import static com.example.reccify.CurrentUserFile.getCurrentUser;
import static com.example.reccify.Genre.genreNames;
import static com.example.reccify.GenreList.genreList;
import static com.example.reccify.GenreList.search;
import static com.example.reccify.MainActivity.sp;
import static com.example.reccify.SpotifyApi.getConnected;
import static com.example.reccify.SpotifyApi.getSpotifyRemote;
import static com.example.reccify.UserFile.readFromFile;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Toast;

import com.example.reccify.databinding.ActivityUserPreferencesBinding;

import java.util.ArrayList;

public class UserPreferencesActivity extends AppCompatActivity {

    @SuppressLint("Range")
    public void selectGenre(View view) {
        String label = view.getResources().getResourceName(view.getId());
        String genreName = "";
        for (int i = 0; i < genreNames.length; i++) {
            int index = label.indexOf(genreNames[i]);
            if (index != -1) {
                genreName = genreNames[i];

                break;
            }
        }
        if (view.getAlpha() != 100) {
            view.setAlpha(100);
            Genre genre = new Genre(genreName);
            genre.setValue(5);
            genreList.add(genre);
            Log.d("UserPreferencesActivity", genre + " added! Yay!");
        } else {
            view.setAlpha(0);
            genreList.remove(search(genreName));
            for (Genre g : genreList) {
                Log.d("genre list", g.toString());
            }
            Log.d("UserPreferencesActivity", genreName + " removed! Yay!");
        }

    }

    public void doneRequest(View view) {
        if (!genreList.isEmpty()) {
            savePreferencesToFile();
            for (Genre g : genreList) {
                Log.d("UserPreferencesActivity", g.toString());
            }
            if (!getSpotifyRemote().isConnected()) {
                getConnected(getApplicationContext(), this);
                sp = getSpotifyRemote();
            }
            Log.d("file yeah", readFromFile(getCurrentUser().getFileName()));
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), ("Select a genre to continue."), Toast.LENGTH_SHORT).show();

        }

    }

    public static void savePreferencesToFile() {
        String s = readFromFile(getCurrentUser().getFileName());

        int index = s.indexOf(",");
        String str = s.substring(0, index);
        getCurrentUser().setFileText(getCurrentUser().getFileName(), str);
        ArrayList<Genre> preferredGenres = new ArrayList<Genre>();

        for (Genre g : genreList) {
            int howMany = g.getValue();
            for (int i = 0; i < howMany; i++)
                preferredGenres.add(g);
        }
        for (Genre g : preferredGenres) {
            getCurrentUser().writeFile("," + g + ", ");
        }
        System.out.println(readFromFile(getCurrentUser().getFileName()));
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
    private final Handler mHideHandler = new Handler();
    private View mContentView;
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
    private View mControlsView;
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
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

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
    private ActivityUserPreferencesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserPreferencesBinding.inflate(getLayoutInflater());
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

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

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

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}