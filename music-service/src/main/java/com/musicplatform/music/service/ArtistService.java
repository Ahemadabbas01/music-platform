package com.musicplatform.music.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.musicplatform.music.dto.ArtistRequest;
import com.musicplatform.music.entity.Artist;

public interface ArtistService {

	// Artist create(Artist artist);

	Artist create(ArtistRequest request);

	Artist getById(Long id);

	Page<Artist> getAll(Pageable pageable);

	Artist update(Long id, ArtistRequest request);

	void delete(Long id);
}
