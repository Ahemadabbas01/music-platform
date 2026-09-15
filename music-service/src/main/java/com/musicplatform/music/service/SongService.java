package com.musicplatform.music.service;

import java.util.List;

import com.musicplatform.music.entity.Song;

public interface SongService {

	Song create(Song song);

	Song getById(Long id);

	List<Song> getAll();

	Song update(Long id, Song song);

	void delete(Long id);
	
	void addArtist(Long songId, Long artistId);

	void removeArtist(Long songId, Long artistId);
}
