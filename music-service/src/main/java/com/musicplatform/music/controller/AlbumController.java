package com.musicplatform.music.controller;

import com.musicplatform.music.dto.AlbumRequest;
import com.musicplatform.music.dto.AlbumResponse;
import com.musicplatform.music.entity.Album;
import com.musicplatform.music.mapper.AlbumMapper;
import com.musicplatform.music.mapper.ArtistMapper;
import com.musicplatform.music.service.AlbumService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse create(@Valid @RequestBody AlbumRequest request) {
         Album created = albumService.create(request);
         return AlbumMapper.toResponse(created);
    }

    @GetMapping("/{id}")
    public AlbumResponse getById(@PathVariable Long id) {
         Album album = albumService.getById(id);
         return AlbumMapper.toResponse(album);
    }

    @GetMapping
    public List<AlbumResponse> getAll() {
        return albumService.getAll().stream().map(AlbumMapper::toResponse).toList();
    }

	@PutMapping("/{id}")
	public AlbumResponse update(@PathVariable Long id, @Valid @RequestBody AlbumRequest request) {
		 Album updated = albumService.update(id, request);
		 
		 return  AlbumMapper.toResponse(updated);
	}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        albumService.delete(id);
    }
}