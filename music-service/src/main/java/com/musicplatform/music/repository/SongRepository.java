package com.musicplatform.music.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musicplatform.music.entity.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface SongRepository extends JpaRepository<Song, Long> {
	@Query(
		    value = """
		            SELECT DISTINCT s FROM Song s
		            LEFT JOIN s.artists a
		            LEFT JOIN s.album al
		            WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :q, '%'))
		               OR LOWER(a.name) LIKE LOWER(CONCAT('%', :q, '%'))
		               OR LOWER(al.name) LIKE LOWER(CONCAT('%', :q, '%'))
		            """,
		    countQuery = """
		            SELECT COUNT(DISTINCT s) FROM Song s
		            LEFT JOIN s.artists a
		            LEFT JOIN s.album al
		            WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :q, '%'))
		               OR LOWER(a.name) LIKE LOWER(CONCAT('%', :q, '%'))
		               OR LOWER(al.name) LIKE LOWER(CONCAT('%', :q, '%'))
		            """
		)
		Page<Song> searchByTitleOrArtistOrAlbum(@Param("q") String q, Pageable pageable);
}
