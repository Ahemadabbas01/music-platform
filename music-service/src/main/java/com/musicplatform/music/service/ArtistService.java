package com.musicplatform.music.service;

import java.util.List;

import com.musicplatform.music.entity.Artist;

public interface ArtistService {

    Artist create(Artist artist);

    Artist getById(Long id);

    List<Artist> getAll();

    Artist update(Long id, Artist artist);

    void delete(Long id);
}
