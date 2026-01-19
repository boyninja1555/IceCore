package com.boyninja1555.icecore.lib;

import java.util.List;

public record MusicMeta(String soundName, String title, String[] authors) {

    public static MusicMeta create(String soundName, String title, String[] authors) {
        return new MusicMeta(soundName, title, authors);
    }

    public static MusicMeta create(String soundName, String title, List<String> authors) {
        return new MusicMeta(soundName, title, authors.toArray(new String[0]));
    }

    public static MusicMeta create(String soundName, String title, String author) {
        return new MusicMeta(soundName, title, new String[]{author});
    }

    public static MusicMeta create(String soundName, String title) {
        return new MusicMeta(soundName, title, new String[]{});
    }
}
