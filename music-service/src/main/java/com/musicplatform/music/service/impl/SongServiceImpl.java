package com.musicplatform.music.service.impl;

import com.musicplatform.music.repository.AlbumRepository;
import java.util.List;

import org.springframework.stereotype.Service;

import com.musicplatform.music.entity.Song;
import com.musicplatform.music.exception.ResourceNotFoundException;
import com.musicplatform.music.repository.SongRepository;
import com.musicplatform.music.service.SongService;
import com.musicplatform.music.dto.SongRequest;
import com.musicplatform.music.entity.Album;
import com.musicplatform.music.entity.Artist;
import com.musicplatform.music.repository.ArtistRepository;

@Service
public class SongServiceImpl implements SongService {

	private final AlbumRepository albumRepository;
	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;

	public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
		this.songRepository = songRepository;
		this.artistRepository = artistRepository;
		this.albumRepository = albumRepository;
	}

	@Override
	public Song create(SongRequest request) {
		Song song = new Song();
	    song.setTitle(request.title());
	    song.setAudioRef(request.audioRef());
	    
	    if (request.albumId() != null) {
	    	 Album album = albumRepository.findById(request.albumId())
	    	            .orElseThrow(() -> new ResourceNotFoundException(
	    	                "Album not found: " + request.albumId()));
	         song.setAlbum(album);

	    }
	    
		return songRepository.save(song);
	}

	@Override
	public Song getById(Long id) {
		return songRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found: " + id));
	}

	@Override
	public List<Song> getAll() {
		return songRepository.findAll();
	}

	@Override
	public Song update(Long id, SongRequest request) {
		  Song existing = songRepository.findById(id)
			        .orElseThrow(() -> new ResourceNotFoundException("Song not found: " + id));

		  existing.setTitle(request.title());
		  existing.setAudioRef(request.audioRef());
		  if (request.albumId() == null) {
		        existing.setAlbum(null);
		  }else {
			  Album album = albumRepository.findById(request.albumId())
			            .orElseThrow(() -> new ResourceNotFoundException(
			                "Album not found: " + request.albumId()));
			        existing.setAlbum(album);
		  }

		return songRepository.save(existing);
	}

	@Override
	public void delete(Long id) {
		Song songData = getById(id);
		songRepository.delete(songData);
	}

	@Override
	public void addArtist(Long songId, Long artistId) {
		Song song = getById(songId);

		Artist artist = artistRepository.findById(artistId)
				.orElseThrow(() -> new ResourceNotFoundException("Artist not found: " + artistId));

		song.getArtists().add(artist);

		songRepository.save(song);

	}

	@Override
	public void removeArtist(Long songId, Long artistId) {

		Song song = getById(songId);

		Artist artist = artistRepository.findById(artistId)
				.orElseThrow(() -> new ResourceNotFoundException("Artist not found: " + artistId));

		song.getArtists().remove(artist);

		songRepository.save(song);
	}

}
