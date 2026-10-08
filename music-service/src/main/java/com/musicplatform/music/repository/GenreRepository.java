package com.musicplatform.music.repository;

import com.musicplatform.music.entity.Genre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Page<Genre> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
    
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

}