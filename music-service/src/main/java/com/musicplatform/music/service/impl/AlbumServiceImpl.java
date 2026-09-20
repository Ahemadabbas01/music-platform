package com.musicplatform.music.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicplatform.music.dto.AlbumRequest;
import com.musicplatform.music.entity.Album;
import com.musicplatform.music.entity.Artist;
import com.musicplatform.music.exception.ResourceNotFoundException;
import com.musicplatform.music.repository.AlbumRepository;
import com.musicplatform.music.repository.ArtistRepository;
import com.musicplatform.music.service.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService {

	private final AlbumRepository albumRepository;
	
	private final ArtistRepository artistRepository;

	public AlbumServiceImpl(AlbumRepository albumRepository,ArtistRepository artistRepository) {
		this.albumRepository = albumRepository;
		this.artistRepository = artistRepository;
	}

	@Override
	public Album create(AlbumRequest request) {
	    Artist artist = artistRepository.findById(request.artistId())
	            .orElseThrow(() -> new ResourceNotFoundException("Artist not found: " + request.artistId()));

	    Album album = new Album();
	    album.setName(request.name());
	    album.setCoverImageRef(request.coverImageRef());
	    album.setArtist(artist);
	    return albumRepository.save(album);
	}

	@Override
	public Album getById(Long id) {
		return albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found: " + id));
	}

	@Override
	public List<Album> getAll() {
		return albumRepository.findAll();
	}

	@Override
	public Album update(Long id, AlbumRequest request) {
		 Album existing = albumRepository.findById(id)
			        .orElseThrow(() -> new ResourceNotFoundException("Album not found: " + id));

		 existing.setName(request.name());
		 existing.setCoverImageRef(request.coverImageRef());

		return albumRepository.save(existing);
	}

	@Override
	public void delete(Long id) {
		Album albumData = getById(id);
		albumRepository.delete(albumData);

	}

}
