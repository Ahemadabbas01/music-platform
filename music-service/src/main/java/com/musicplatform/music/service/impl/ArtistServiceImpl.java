package com.musicplatform.music.service.impl;

import com.musicplatform.music.dto.ArtistRequest;
import com.musicplatform.music.entity.Artist;
import com.musicplatform.music.exception.ResourceNotFoundException;
import com.musicplatform.music.repository.ArtistRepository;
import com.musicplatform.music.service.ArtistService;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImpl implements ArtistService {

	private final ArtistRepository artistRepository;

	public ArtistServiceImpl(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	@Override
	public Artist create(ArtistRequest request) {
	    Artist artist = new Artist();
	    artist.setName(request.name());
	    artist.setDetails(request.details());
	    artist.setCountry(request.country());
	    return artistRepository.save(artist);
	}

	@Override
	public Artist getById(Long id) {
		return artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found: " + id));
	}

	@Override
	public List<Artist> getAll() {
		return artistRepository.findAll();
	}

	@Override
	public Artist update(Long id, ArtistRequest request) {
	    Artist existing = artistRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Artist not found: " + id));
	    existing.setName(request.name());
	    existing.setDetails(request.details());
	    existing.setCountry(request.country());
	    return artistRepository.save(existing);
	}

	@Override
	public void delete(Long id) {
		Artist artistData = getById(id);
		artistRepository.delete(artistData);
	}

}
