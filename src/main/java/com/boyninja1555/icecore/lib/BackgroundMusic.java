package com.boyninja1555.icecore.lib;

public enum BackgroundMusic {
    OVERTAKE_THAT_KID(MusicMeta.create("overtake-that-kid", "Overtake That Kid"));

    private final MusicMeta meta;

    BackgroundMusic(MusicMeta meta) {
        this.meta = meta;
    }

    public MusicMeta meta() {
        return meta;
    }
}
