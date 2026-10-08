package com.musicplatform.music.dto;

import java.time.OffsetDateTime;

public record GenreResponse(Long id,
		String name,
		String description,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt) {

}
