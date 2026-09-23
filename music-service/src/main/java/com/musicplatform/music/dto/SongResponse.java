package com.musicplatform.music.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record SongResponse(Long id,
		String title,
		String audioRef,
		Long albumId,
		String albumName,
		List<ArtistSummary> artists,
		OffsetDateTime createdAt,
		OffsetDateTime updatedAt) {

}
