package com.musicplatform.music.mapper;

import com.musicplatform.music.dto.ArtistResponse;
import com.musicplatform.music.entity.Artist;

public class ArtistMapper {

	private ArtistMapper() {

	}

	public static ArtistResponse toResponse(Artist artist) {
		return new ArtistResponse(artist.getId(), artist.getName(), artist.getDetails(), artist.getCountry(),
				artist.getCreatedAt(), artist.getUpdatedAt());
	}
}
