package com.example.reccify;

public class Genre {

    public static final String[][] playlists = {
            {"Alternative", "spotify:playlist:37i9dQZF1DX9GRpeH4CL0S?si=145a140b79ea40c3", "spotify:playlist:37i9dQZF1DWVqJMsgEN0F4?si=e7a928bb3b37497d", "spotify:playlist:37i9dQZF1DX873GaRGUmPl?si=b76dcf3164054df5"},
            {"Ambience", "spotify:playlist:37i9dQZF1DX9c7yCloFHHL?si=7a72b70b8c4a43ac", "spotify:playlist:37i9dQZF1DWUrPBdYfoJvz?si=735323038f164234", "spotify:playlist:37i9dQZF1DXdf43Md5h6cE?si=38898cdc6a854d42"},
            {"Anime", "spotify:playlist:37i9dQZF1DX0hAXqBDwvwI?si=0ca9313473344a29", "spotify:playlist:37i9dQZF1DWT8aqnwgRt92?si=c0b708ecdc4f48ad", "spotify:playlist:37i9dQZF1DX7GTqMQDhOum?si=fc95b0d1e4924a55"},
            {"Arabic", "spotify:playlist:37i9dQZF1DX5cO1uP1XC1g?si=8b4c867424fd4096", "spotify:playlist:37i9dQZF1DXd3AhRYJnfcl?si=76eaf98a36a344a0", "spotify:playlist:37i9dQZF1DWYHO8PTSQ9fM?si=41a35c66d1ad4b37"},
            {"Blues", "spotify:playlist:37i9dQZF1DWYi488IywmOA?si=84f78c78f89a40e4", "spotify:playlist:37i9dQZF1DXd9rSDyQguIk?si=738fb51743d043af", "spotify:playlist:37i9dQZF1DX0QNpebF7rcL?si=a3b43eafdb98495d"},
            {"Chill", "spotify:playlist:37i9dQZF1DX4WYpdgoIcn6?si=88195009202747c4", "spotify:playlist:37i9dQZF1DWTwnEm1IYyoj?si=6e10b9149b894bb8", "spotify:playlist:37i9dQZF1DX6VdMW310YC7?si=42bbc333867348d2"},
            {"Classical", "spotify:playlist:37i9dQZF1DWWEJlAGA9gs0?si=42af3b053d524971", "spotify:playlist:37i9dQZF1DWV0gynK7G6pD?si=a7b6a0a1eac748f4", "spotify:playlist:37i9dQZF1DX17GkScaAekA?si=018b8ea645394736"},
            {"Country", "spotify:playlist:37i9dQZF1DWSK8os4XIQBk?si=e2c8d89f69884c27", "spotify:playlist:37i9dQZF1DX1lVhptIYRda?si=afbb2dc8b80e422f", "spotify:playlist:37i9dQZF1DX13ZzXoot6Jc?si=cc4f7125e2bd460b"},
            {"Desi", "spotify:playlist:37i9dQZF1DWTwzVdyRpXm1?si=1d24ee6906f149f4", "spotify:playlist:37i9dQZF1DX2RahGIyQXcJ?si=67901be26e894649", "spotify:playlist:37i9dQZF1DXdQoEaDx51WH?si=7a60a8b234a9414a"},
            {"EDM", "spotify:playlist:37i9dQZF1DWZCdOD1jAvLL?si=60a2aa05e65a4b14", "spotify:playlist:37i9dQZF1DXa2PvUpywmrr?si=fafbf968643040ff", "spotify:playlist:37i9dQZF1DWWY64wDtewQt?si=13427e8889314078"},
            {"French", "spotify:playlist:6TxuoHMx9qLTePZGS1CasY?si=23cc6adb46b2420f", "spotify:playlist:2lIR7D35E0MIQrQiNFJMUi?si=71170f368f934571", "spotify:playlist:5JEWr5PlRJT3uNz2EGaOOg?si=f606d2d8e9d04ffb"},
            {"Funk", "spotify:playlist:37i9dQZF1DWZgauS5j6pMv?si=da22b7f289d0420d", "spotify:playlist:37i9dQZF1DWUcRrhkfhG22?si=84acf15dbd654f04", "spotify:playlist:37i9dQZF1DX1MUPbVKMgJE?si=619ed20505bd4289"},
            {"HipHop", "spotify:playlist:37i9dQZF1DWY6tYEFs22tT?si=df8dc4f62bba4aaf", "spotify:playlist:37i9dQZF1DX0XUsuxWHRQd?si=7e1f99cafa704d93", "spotify:playlist:37i9dQZF1DX48TTZL62Yht?si=560bc34c43654a48"},
            {"HyperPop", "spotify:playlist:37i9dQZF1DX7HOk71GPfSw?si=07c3af2473d64939", "spotify:playlist:7HJ1bHvvqwIQFluMmw548S?si=bcfd4ae7d6b34ef3", "spotify:playlist:0DBFQTRWGvV9VCoKuwdNXq?si=c19de869659e4828"},
            {"Indie", "spotify:playlist:37i9dQZF1DX2sUQwD7tbmL?si=4af07fc4d23840cb", "spotify:playlist:37i9dQZF1DX26DKvjp0s9M?si=05cbb1955a064015", "spotify:playlist:37i9dQZF1DWWEcRhUVtL8n?si=3c7e30ae484248fe"},
            {"Jazz", "spotify:playlist:37i9dQZF1DX7YCknf2jT6s?si=44c33f0cdad94d3a", "spotify:playlist:37i9dQZF1DXbITWG1ZJKYt?si=350510efe56540b5", "spotify:playlist:37i9dQZF1DWW2c0C8Vb2IR?si=7ccb66e208be45cc"},
            {"KPop", "spotify:playlist:37i9dQZF1DX9tPFwDMOaN1?si=8b40aec0cd36467a", "spotify:playlist:37i9dQZF1DX4FcAKI5Nhzq?si=c2c3f9355bf74866", "spotify:playlist:37i9dQZF1DXe5W6diBL5N4?si=b866d1ce1a0d4b22"},
            {"Latin", "spotify:playlist:37i9dQZF1DX10zKzsJ2jva?si=4cd4fb259a684332", "spotify:playlist:37i9dQZF1DWVcbzTgVpNRm?si=316c6fc0c2ec4bcf", "spotify:playlist:37i9dQZF1DXbLMw3ry7d7k?si=ab88dab8667d4ab0"},
            {"LoFi", "spotify:playlist:37i9dQZF1DWWQRwui0ExPn?si=7a88320d34be4b82", "spotify:playlist:0vvXsWCC9xrXsKd4FyS8kM?si=5cb1bc1291b04c13", "spotify:playlist:4x9OtLt7bsmvqktbF0Y0Gm?si=0d23603939234b25"},
            {"Metal", "spotify:playlist:37i9dQZF1DX2LTcinqsO68?si=ac58b3d70bcd453a", "spotify:playlist:37i9dQZF1DWXNFSTtym834?si=d36b5a8a8a1a4b52", "spotify:playlist:37i9dQZF1DXcfZ6moR6J0G?si=6437038cdbc841b6"},
            {"Pop", "spotify:playlist:37i9dQZF1DXarRysLJmuju?si=d82473cd06a146c0", "spotify:playlist:37i9dQZF1DWYMfG0Phlxx8?si=0a2c5e2a0b2e49df", "spotify:playlist:37i9dQZF1DXcOFePJj4Rgb?si=cde7ab6524504904"},
            {"Punk", "spotify:playlist:37i9dQZF1DXasneILDRM7B?si=01f0d6b5db21489c", "spotify:playlist:37i9dQZF1DXa9wYJr1oMFq?si=b901e18bb02b433a", "spotify:playlist:37i9dQZF1DX0KpeLFwA3tO?si=b9a588a517884a4d"},
            {"RnB", "spotify:playlist:37i9dQZF1DX7FY5ma9162x?si=98bdabd8ae764ea7", "spotify:playlist:37i9dQZF1DX4SBhb3fqCJd?si=bd7f3f65ddf64af5", "spotify:playlist:37i9dQZF1DX2WkIBRaChxW?si=4a8b311cb8934e32"},
            {"Soul", "spotify:playlist:37i9dQZF1DWULEW2RfoSCi?si=28ec02d10dff4d7b", "spotify:playlist:37i9dQZF1DWTx0xog3gN3q?si=6a2342e08efb4b5f", "spotify:playlist:37i9dQZF1DX70TzPK5buVf?si=6824b71e7d9b4529"},
            {"UK", "spotify:playlist:37i9dQZF1DX6PKX5dyBKeq?si=a9e7caab05d7480f", "spotify:playlist:6g7N0ffrQjk5BUk9GrAH53?si=f2dcb1bf0f6f4366", "spotify:playlist:6nuo9FYCAGV0WalhCluicG?si=5c366668bed144c4"}
    };
    public static final String[] genreNames = {"Alternative", "Ambience", "Anime", "Arabic", "Blues", "Chill", "Classical", "Country", "Desi", "EDM", "French", "Funk", "HipHop", "HyperPop", "Indie", "Jazz", "KPop", "Latin", "LoFi", "Metal", "Pop", "Punk", "RnB", "Soul", "UK"};
    private final String genreName;
    private int popularity = 0;

    public Genre(String genre) {
        genreName = genre;
        popularity = 0;

    }

    public void setValue(int value) {
        popularity = value;
    }

    public int getValue() {
        return popularity;
    }

    public void addValue(int value) {
        popularity += value;
    }

    public void subtractValue(int value) {
        popularity -= value;
    }

    public String[] getPlaylists() {

        for (int r = 0; r < playlists.length; r++) {
            if (playlists[r][0].equals(genreName)) {
                int count = 1;
                String[] temp = new String[playlists[r].length - 1];
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = playlists[r][count];
                    count++;

                }
                return temp;
            }

        }
        return null;
    }

    @Override
    public String toString() {
        return genreName;
    }


}