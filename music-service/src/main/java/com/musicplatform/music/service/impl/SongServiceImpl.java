package com.musicplatform.music.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicplatform.music.entity.Song;
import com.musicplatform.music.exception.ResourceNotFoundException;
import com.musicplatform.music.repository.SongRepository;
import com.musicplatform.music.service.SongService;
import com.musicplatform.music.entity.Artist;
import com.musicplatform.music.repository.ArtistRepository;

@Service
public class SongServiceImpl implements SongService {

	private final SongRepository songRepository;
	private final ArtistRepository artistRepository;

	public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository) {
		this.songRepository = songRepository;
		this.artistRepository = artistRepository;
	}

	@Override
	public Song create(Song song) {
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
	public Song update(Long id, Song song) {
		Song songData = getById(id);

		songData.setTitle(song.getTitle());
		songData.setAudioRef(song.getAudioRef());
		songData.setAlbum(song.getAlbum());

		return songRepository.save(songData);
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
