package com.musicplatform.music.service;

import java.util.List;

import com.musicplatform.music.dto.AlbumRequest;
import com.musicplatform.music.entity.Album;

public interface AlbumService {

	Album create(AlbumRequest request);

	Album getById(Long id);

	List<Album> getAll();

	Album update(Long id, AlbumRequest request);

	void delete(Long id);
}
