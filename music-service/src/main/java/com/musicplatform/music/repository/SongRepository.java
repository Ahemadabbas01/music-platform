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
		            WHERE (CAST(:q AS String) IS NULL OR
		                   LOWER(s.title) LIKE LOWER(CONCAT('%', CAST(:q AS String), '%'))
		                   OR LOWER(a.name) LIKE LOWER(CONCAT('%', CAST(:q AS String), '%'))
		                   OR LOWER(al.name) LIKE LOWER(CONCAT('%', CAST(:q AS String), '%')))
		              AND (CAST(:artistId AS Long) IS NULL OR a.id = :artistId)
		              AND (CAST(:albumId AS Long) IS NULL OR al.id = :albumId)
		            """,
		    countQuery = """
		            SELECT COUNT(DISTINCT s) FROM Song s
		            LEFT JOIN s.artists a
		            LEFT JOIN s.album al
		            WHERE (CAST(:q AS String) IS NULL OR
		                   LOWER(s.title) LIKE LOWER(CONCAT('%', CAST(:q AS String), '%'))
		                   OR LOWER(a.name) LIKE LOWER(CONCAT('%', CAST(:q AS String), '%'))
		                   OR LOWER(al.name) LIKE LOWER(CONCAT('%', CAST(:q AS String), '%')))
		              AND (CAST(:artistId AS Long) IS NULL OR a.id = :artistId)
		              AND (CAST(:albumId AS Long) IS NULL OR al.id = :albumId)
		            """
		)
		Page<Song> search(
		    @Param("q") String q,
		    @Param("artistId") Long artistId,
		    @Param("albumId") Long albumId,
		    Pageable pageable
		);
}
