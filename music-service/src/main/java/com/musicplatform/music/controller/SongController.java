package com.musicplatform.music.controller;

import com.musicplatform.music.dto.SongRequest;
import com.musicplatform.music.dto.SongResponse;
import com.musicplatform.music.entity.Song;
import com.musicplatform.music.mapper.SongMapper;
import com.musicplatform.music.service.SongService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.musicplatform.music.dto.PageResponse;

@RestController
@RequestMapping("/api/songs")
public class SongController {

	private final SongService songService;

	public SongController(SongService songService) {
		this.songService = songService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SongResponse create(@Valid @RequestBody SongRequest request) {
		 Song created = songService.create(request);
		 return SongMapper.toResponse(created);
	}

	@GetMapping("/{id}")
	public SongResponse getById(@PathVariable Long id) {
		 Song song = songService.getById(id);
		 return SongMapper.toResponse(song);
	}

	@GetMapping
	public PageResponse<SongResponse> getAll(Pageable pageable) {
		Page<Song> page = songService.getAll(pageable);

		List<SongResponse> content = page.getContent().stream().map(SongMapper::toResponse).toList();

		return new PageResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isFirst(), page.isLast());
	}

	@PutMapping("/{id}")
	public SongResponse update(@PathVariable Long id,@Valid @RequestBody SongRequest request) {
		 Song updated = songService.update(id, request);
		 return SongMapper.toResponse(updated);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		songService.delete(id);
	}

	@PostMapping("/{songId}/artists/{artistId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addArtist(@PathVariable Long songId, @PathVariable Long artistId) {

		songService.addArtist(songId, artistId);
	}

	@DeleteMapping("/{songId}/artists/{artistId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeArtist(@PathVariable Long songId, @PathVariable Long artistId) {

		songService.removeArtist(songId, artistId);
	}
}