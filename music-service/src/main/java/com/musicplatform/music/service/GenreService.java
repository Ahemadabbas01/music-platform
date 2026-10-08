package com.musicplatform.music.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.musicplatform.music.dto.GenreRequest;
import com.musicplatform.music.entity.Genre;

public interface GenreService {
	Genre create(GenreRequest request);

    Genre getById(Long id);

    Page<Genre> search(String q, Pageable pageable);

    Genre update(Long id, GenreRequest request);

    void delete(Long id);
}
