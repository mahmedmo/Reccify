package com.example.reccify;

import java.util.ArrayList;

class GenreList {

    public static ArrayList<Genre> genreList = new ArrayList<Genre>();

    public static Genre favouriteGenre() {
        int high = 0;
        int count = 0;
        for (Genre g : genreList) {

            if (g.getValue() > genreList.get(high).getValue()) {
                high = count;
            }
            count++;
        }
        return genreList.get(high);
    }

    //Gets a genre that the user prefers.
    public static Genre getRandomGenre() {
        ArrayList<Genre> preferredGenres = new ArrayList<Genre>();
        for (Genre g : genreList) {
            int howMany = g.getValue();
            for (int i = 0; i < howMany; i++)
                preferredGenres.add(g);
        }


        int rndm = (int) (Math.random() * preferredGenres.size());

        return preferredGenres.get(rndm);

    }

    public static int search(String genreName) {
        int key = 0;
        int count = 0;
        for (Genre g : genreList) {
            if (g.toString().equals(genreName)) {
                key = count;
                break;
            }
            count++;
        }
        return key;
    }

}
