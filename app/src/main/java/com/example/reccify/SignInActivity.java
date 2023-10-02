package com.example.reccify;

import static com.example.reccify.CurrentUserFile.getCurrentUser;
import static com.example.reccify.CurrentUserFile.setCurrentUser;
import static com.example.reccify.Genre.genreNames;
import static com.example.reccify.GenreList.genreList;
import static com.example.reccify.MainActivity.likedArtists;
import static com.example.reccify.MainActivity.likedSongs;
import static com.example.reccify.UserFile.readFromFile;
import static com.example.reccify.UserFile.searchForFile;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reccify.databinding.ActivitySignInBinding;

import java.io.File;
import java.util.ArrayList;


public class SignInActivity extends AppCompatActivity {
    TextView mInputUsername;
    TextView mInputPassword;

    public static Context context;

    public void signIn(View view) {
        String username = mInputUsername.getText().toString();
        String password = mInputPassword.getText().toString();
        Log.d("file", readFromFile(username + ".txt"));
        if (searchForFile(username + ".txt", username, password)) {
            UserFile user = new UserFile(username + ".txt", username, password);
            setCurrentUser(user);

            Log.d("file", readFromFile(user.getFileName()));
            retrievePreferencesFromFile();
            retrieveArtistsFromFile();
            retrieveLikedSongsFromFile();
            for (Genre g : genreList) {
                Log.d("GenreList", g.toString());
            }
            Toast.makeText(getApplicationContext(), ("Success!"), Toast.LENGTH_SHORT).show();

            Intent mainContent = new Intent(this, MainActivity.class);
            startActivity(mainContent);

        } else {
            Toast.makeText(getApplicationContext(), ("Username or Password is Incorrect"), Toast.LENGTH_SHORT).show();
        }

    }

    public void backRequest(View view) {
        onBackPressed();
    }

    public void retrievePreferencesFromFile() {
        ArrayList<Genre> retrievedGenreList = new ArrayList<Genre>();
        String fileContent = readFromFile(getCurrentUser().getFileName());

        String[] fileContents = fileContent.split(",");
        for (String s : fileContents) {
            Log.d("STRING ARRAY", s);
        }

        String genreName = "";
        int popularityCount = 0;
        boolean genreFound = false;
        for (int i = 0; i < genreNames.length; i++) {
            for (int j = 0; j < fileContents.length; j++) {
                int index = fileContents[j].indexOf(genreNames[i]);
                if (index != -1) {
                    genreName = genreNames[i];
                    popularityCount++;
                    genreFound = true;
                }
            }
            if (genreFound) {
                Genre genre = new Genre(genreName);
                genre.setValue(popularityCount);
                retrievedGenreList.add(genre);
                genreFound = false;
                genreName = "";
                popularityCount = 0;
            }
        }
        genreList = retrievedGenreList;
        for (Genre g : genreList) {
            Log.d("GENRE LIST", g.toString());
            Log.d("GENRE LIST", "" + g.getValue());
        }

    }

    public void retrieveLikedSongsFromFile() {
        String fileContent = readFromFile(getCurrentUser().getFileName());
        String[] fileContents = fileContent.split("\\*");
        for (int i = 1; i < fileContents.length; i = i + 2) {
            likedSongs.add(fileContents[i]);
        }

    }

    public void retrieveArtistsFromFile() {
        String fileContent = readFromFile(getCurrentUser().getFileName());
        String[] fileContents = fileContent.split(";");
        for (int i = 1; i < fileContents.length; i = i + 2) {
            likedArtists.add(fileContents[i]);
        }

    }

    //FULLSCREEN SETUP BELOW

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
            // Delayed display of UI elements
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
    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
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
        mInputUsername = findViewById(R.id.inputUsername);
        mInputPassword = findViewById(R.id.inputPassword);
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