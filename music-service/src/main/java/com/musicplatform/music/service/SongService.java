package com.musicplatform.music.service;

import java.util.List;

import com.musicplatform.music.dto.SongRequest;
import com.musicplatform.music.entity.Song;

public interface SongService {

	Song create(SongRequest request);

	Song getById(Long id);

	List<Song> getAll();

	Song update(Long id, SongRequest request);

	void delete(Long id);
	
	void addArtist(Long songId, Long artistId);

	void removeArtist(Long songId, Long artistId);
}
