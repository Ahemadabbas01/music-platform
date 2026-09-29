package com.musicplatform.music.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musicplatform.music.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
	
	Page<Artist> findByNameStartingWithIgnoreCase(String q,Pageable pageable);
}