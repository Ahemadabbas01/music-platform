package com.musicplatform.music.mapper;

import com.musicplatform.music.dto.GenreResponse;
import com.musicplatform.music.entity.Genre;

public final class GenreMapper {

	private GenreMapper() {}
	
	public static GenreResponse toResponse(Genre genre) {
		return new GenreResponse(
				genre.getId(),
                genre.getName(),
                genre.getDescription(),
                genre.getCreatedAt(),
                genre.getUpdatedAt()
         );
	}
}
