package com.musicplatform.music.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "song_artist")
@IdClass(SongArtistId.class)
public class SongArtist {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    public SongArtist() {
    }

    public SongArtist(Song song, Artist artist) {
        this.song = song;
        this.artist = artist;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}