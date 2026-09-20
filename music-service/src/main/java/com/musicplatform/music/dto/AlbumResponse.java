package com.musicplatform.music.dto;

import java.time.OffsetDateTime;

public record AlbumResponse(
		Long id,
        String name,
        String coverImageRef,
        Long artistId,
        String artistName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {

}
