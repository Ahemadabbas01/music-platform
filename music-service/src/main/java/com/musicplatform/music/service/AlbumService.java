package com.musicplatform.music.service;

import java.util.List;

import com.musicplatform.music.entity.Album;

public interface AlbumService {

	Album create(Album album);

	Album getById(Long id);

	List<Album> getAll();

	Album update(Long id, Album album);

	void delete(Long id);
}
