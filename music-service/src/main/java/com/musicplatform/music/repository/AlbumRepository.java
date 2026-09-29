package com.musicplatform.music.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musicplatform.music.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
	
	Page<Album> findByNameStartingWithIgnoreCase(String q,Pageable pageable);
}