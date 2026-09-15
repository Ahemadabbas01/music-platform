package com.musicplatform.music.controller;

import com.musicplatform.music.entity.Album;
import com.musicplatform.music.service.AlbumService;
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
    public Album create(@RequestBody Album album) {
        return albumService.create(album);
    }

    @GetMapping("/{id}")
    public Album getById(@PathVariable Long id) {
        return albumService.getById(id);
    }

    @GetMapping
    public List<Album> getAll() {
        return albumService.getAll();
    }

    @PutMapping("/{id}")
    public Album update(
            @PathVariable Long id,
            @RequestBody Album album) {
        return albumService.update(id, album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        albumService.delete(id);
    }
}