package com.musicplatform.music.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicplatform.music.entity.Album;
import com.musicplatform.music.entity.Artist;
import com.musicplatform.music.exception.ResourceNotFoundException;
import com.musicplatform.music.repository.AlbumRepository;
import com.musicplatform.music.service.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService {

	private final AlbumRepository albumRepository;

	public AlbumServiceImpl(AlbumRepository albumRepository) {
		this.albumRepository = albumRepository;
	}

	@Override
	public Album create(Album album) {
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
	public Album update(Long id, Album album) {
		Album albumData = getById(id);

		albumData.setName(album.getName());
		albumData.setArtist(album.getArtist());
		albumData.setCoverImageRef(album.getCoverImageRef());

		return albumRepository.save(albumData);
	}

	@Override
	public void delete(Long id) {
		Album albumData = getById(id);
		albumRepository.delete(albumData);

	}

}
