package com.example.reccify;

public class SavedSongsModel {
    String saveSong;
    int image;

    public SavedSongsModel(String song, int image) {
        this.image = image;
        saveSong = song;
    }

    public String getSaveSong() {
        return saveSong;
    }

    public int getImage() {
        return image;
    }

}
