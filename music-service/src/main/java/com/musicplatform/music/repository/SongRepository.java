package com.musicplatform.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicplatform.music.entity.Song;


public interface SongRepository extends JpaRepository<Song, Long> {

}
