package com.example.reccify;

import static com.example.reccify.MainActivity.sp;
import static com.example.reccify.MainActivity.trackHistory;
import static com.example.reccify.ListenFragment.mCoverArtImageView;

import static com.example.reccify.ListenFragment.mSeekBar;
import static com.example.reccify.ListenFragment.mTrackProgressBar;
import static com.example.reccify.ListenFragment.mTrackView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.Image;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import java.util.Locale;

public class SpotifyApi {

    private static final String CLIENT_ID = "a5bb1fbdc5244ef9b875e81cdea3367c";
    private static final String REDIRECT_URI = "comreccify://callback";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    private static SpotifyAppRemote mSpotifyAppRemote;
    private static boolean activityChanged = false;
    static Subscription<PlayerState> mPlayerStateSubscription;
    static Track track;
    static String trackName;

    public static void spotifyLogin(Context context, Activity activity) {
        final AuthorizationRequest request = getAuthentication(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(activity, AUTH_TOKEN_REQUEST_CODE, request);
        getConnected(context, activity);
    }

    public static AuthorizationRequest getAuthentication(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, REDIRECT_URI)
                .build();
    }

    public static boolean getActivityChanged() {
        return activityChanged;
    }

    public static void setActivityChanged(boolean change) {
        activityChanged = change;
    }

    public static SpotifyAppRemote getSpotifyRemote() {
        return mSpotifyAppRemote;
    }

    public static void getConnected(Context context, Activity activity) {

        SpotifyAppRemote.disconnect(mSpotifyAppRemote);

        SpotifyAppRemote.connect(
                context.getApplicationContext(),
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build(),
                new Connector.ConnectionListener() {
                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("SpotifyApi", "Connected! Yay!");

                        connected(activity);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("SpotifyApi", throwable.getMessage(), throwable);
                    }
                });
    }

    public static void connected(Activity activity) {
        sp = getSpotifyRemote();

        if (!activityChanged) {
            Intent accountLogin = new Intent(activity, AccountActivity.class);
            activity.startActivity(accountLogin);
        }
        activityChanged = true;


    }

    public static void getDisconnected() throws InterruptedException {
        stopPlayback();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
        SpotifyAppRemote.disconnect(sp);

    }

    public static final Subscription.EventCallback<PlayerState> mPlayerStateEventCallback =
            new Subscription.EventCallback<PlayerState>() {
                @Override
                public void onEvent(PlayerState playerState) {
                    track = playerState.track;
                    trackName = playerState.track.name;
                    MainActivity.playerstate = playerState;
                    if (playerState.track != null) {
                        // Get image from track
                        mSpotifyAppRemote
                                .getImagesApi()
                                .getImage(playerState.track.imageUri, Image.Dimension.LARGE)
                                .setResultCallback(
                                        bitmap -> {
                                            mCoverArtImageView.setImageBitmap(bitmap);
                                        });

                    }
                    if (playerState.track != null) {
                        mTrackView.setText(String.format(
                                Locale.US, "%s\n%s", track.name, track.artist.name));
                    }
                    if (playerState.track != null) {
                        mSeekBar.setMax((int) playerState.track.duration);
                        mTrackProgressBar.setDuration(playerState.track.duration - 30000);
                        mTrackProgressBar.update(playerState.playbackPosition);
                        mTrackProgressBar.update(playerState.playbackPosition);
                    }
                    if (playerState.track != null) {
                        trackHistory.add(track.uri);
                    }
                    mSeekBar.setEnabled(true);

                }

            };

    public static void onSubscribedToPlayerStateButtonClicked(View view) {
        if (mPlayerStateSubscription != null && !mPlayerStateSubscription.isCanceled()) {
            mPlayerStateSubscription = null;
        }
        mPlayerStateSubscription =
                (Subscription<PlayerState>)
                        mSpotifyAppRemote
                                .getPlayerApi()
                                .subscribeToPlayerState()
                                .setEventCallback(mPlayerStateEventCallback)
                                .setLifecycleCallback(
                                        new Subscription.LifecycleCallback() {
                                            @Override
                                            public void onStart() {
                                                Log.d("SpotifyApi", "image is showing! Yay!");
                                            }

                                            @Override
                                            public void onStop() {
                                                Log.d("SpotifyApi", "image gone! Yay!");
                                            }
                                        })
                                .setErrorCallback(
                                        throwable -> {


                                        });
    }

    public static void stopPlayback() throws InterruptedException {
        mSpotifyAppRemote.getPlayerApi().pause();
        Thread.sleep(500);

    }


}
