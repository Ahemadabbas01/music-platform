package com.musicplatform.music.mapper;

import java.util.Comparator;
import java.util.List;

import com.musicplatform.music.dto.ArtistSummary;
import com.musicplatform.music.dto.SongResponse;
import com.musicplatform.music.entity.Album;
import com.musicplatform.music.entity.Genre;
import com.musicplatform.music.entity.Song;

public class SongMapper {

	private SongMapper() {}
	
	public static SongResponse toResponse(Song song) {
		
		Album album = song.getAlbum();
		Long albumId = (album == null) ? null : album.getId();
		String albumName = (album == null) ? null : album.getName();
	      
		Genre genre = song.getGenre();
		Long genreId = (genre == null) ? null : genre.getId();
		String genreName = (genre == null) ? null : genre.getName();
		
		List<ArtistSummary> artists = song.getArtists().stream()
                .map(a -> new ArtistSummary(a.getId(), a.getName()))
                .sorted(Comparator.comparing(ArtistSummary::name))
                .toList();
		
		return new SongResponse(song.getId(),
				song.getTitle(),
				song.getAudioRef(),
				albumId,
				albumName,
				genreId,
		        genreName, 
				artists,
				song.getCreatedAt(),
				song.getUpdatedAt());
		}
}
