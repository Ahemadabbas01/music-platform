package com.musicplatform.music.service;

import java.util.List;

import com.musicplatform.music.dto.ArtistRequest;
import com.musicplatform.music.entity.Artist;

public interface ArtistService {

	// Artist create(Artist artist);

	Artist create(ArtistRequest request);

	Artist getById(Long id);

	List<Artist> getAll();

	Artist update(Long id, ArtistRequest request);

	void delete(Long id);
}
