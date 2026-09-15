package com.musicplatform.music.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.musicplatform.music.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}