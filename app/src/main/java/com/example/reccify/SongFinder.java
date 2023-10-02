package com.example.reccify;

import static com.example.reccify.GenreList.search;
import static com.example.reccify.SpotifyApi.getSpotifyRemote;

import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;

public class SongFinder {
    private static SpotifyAppRemote appRemote = getSpotifyRemote();
    private static PlayerApi player = appRemote.getPlayerApi();
    private static String currentPlaylist = "";
    private static boolean isPlaying = false;
    private static Genre currentGenre;

    public static String playlist(Genre genre) {

        currentGenre = genre;
        String[] playlists = genre.getPlaylists();
        int random = (int) (Math.random() * playlists.length);
        return playlists[random];

    }

    public static void like() {
        int key = search(currentGenre.toString());
        GenreList.genreList.get(key).addValue(1);
    }

    public static void dislike() {
        int key = search(currentGenre.toString());
        int newPopularity = GenreList.genreList.get(key).getValue() - 1;
        if (newPopularity != 0) {
            GenreList.genreList.get(key).subtractValue(1);
        }

    }

}