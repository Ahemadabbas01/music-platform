package com.musicplatform.music.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.musicplatform.music.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}