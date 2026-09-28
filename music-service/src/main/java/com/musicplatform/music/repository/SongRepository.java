package com.musicplatform.music.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musicplatform.music.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
	Page<Song> findByTitleStartingWithIgnoreCase(String title, Pageable pageable);

}
