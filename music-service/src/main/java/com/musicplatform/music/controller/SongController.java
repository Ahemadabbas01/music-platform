package com.musicplatform.music.controller;

import com.musicplatform.music.entity.Song;
import com.musicplatform.music.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

	private final SongService songService;

	public SongController(SongService songService) {
		this.songService = songService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Song create(@RequestBody Song song) {
		return songService.create(song);
	}

	@GetMapping("/{id}")
	public Song getById(@PathVariable Long id) {
		return songService.getById(id);
	}

	@GetMapping
	public List<Song> getAll() {
		return songService.getAll();
	}

	@PutMapping("/{id}")
	public Song update(@PathVariable Long id, @RequestBody Song song) {
		return songService.update(id, song);
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