package com.musicplatform.music.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.musicplatform.music.dto.AlbumRequest;
import com.musicplatform.music.entity.Album;

public interface AlbumService {

	Album create(AlbumRequest request);

	Album getById(Long id);

	Page<Album> getAll(Pageable pageable);

	Album update(Long id, AlbumRequest request);

	void delete(Long id);
}
