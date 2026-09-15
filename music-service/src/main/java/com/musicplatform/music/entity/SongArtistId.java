package com.musicplatform.music.entity;

import java.io.Serializable;
import java.util.Objects;

public class SongArtistId implements Serializable {

    private Long song;
    private Long artist;

    public SongArtistId() {
    }

    public SongArtistId(Long song, Long artist) {
        this.song = song;
        this.artist = artist;
    }

    public Long getSong() {
        return song;
    }

    public void setSong(Long song) {
        this.song = song;
    }

    public Long getArtist() {
        return artist;
    }

    public void setArtist(Long artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongArtistId that)) return false;
        return Objects.equals(song, that.song)
                && Objects.equals(artist, that.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(song, artist);
    }
}