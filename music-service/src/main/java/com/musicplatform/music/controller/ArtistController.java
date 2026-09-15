package com.musicplatform.music.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.musicplatform.music.dto.ArtistResponse;
import com.musicplatform.music.entity.Artist;
import com.musicplatform.music.mapper.ArtistMapper;
import com.musicplatform.music.service.ArtistService;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

	private final ArtistService artistService;

	public ArtistController(ArtistService artistService) {
		this.artistService = artistService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Artist create(@RequestBody Artist artist) {
		return artistService.create(artist);
	}

	@GetMapping("/{id}")
	public ArtistResponse getById(@PathVariable Long id) {
		Artist artistData = artistService.getById(id);
		return ArtistMapper.toResponse(artistData);
	}

	@GetMapping
	public List<ArtistResponse> getAll() {
		return artistService.getAll().stream().map(ArtistMapper::toResponse).toList();
	}

	@PutMapping("/{id}")
	public Artist update(@PathVariable Long id, @RequestBody Artist artist) {
		return artistService.update(id, artist);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		artistService.delete(id);
	}
}