package com.musicplatform.music.mapper;

import com.musicplatform.music.dto.AlbumResponse;
import com.musicplatform.music.entity.Album;

public class AlbumMapper {

	private AlbumMapper() {}
	
	public static AlbumResponse toResponse(Album album) {
		return new AlbumResponse(
				album.getId(),
		        album.getName(),
		        album.getCoverImageRef(),
		        album.getArtist().getId(),
		        album.getArtist().getName(),
		        album.getCreatedAt(),
		        album.getUpdatedAt()
		);
	}
}
