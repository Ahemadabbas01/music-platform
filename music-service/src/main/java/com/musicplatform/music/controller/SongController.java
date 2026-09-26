package com.musicplatform.music.controller;

import com.musicplatform.music.dto.SongRequest;
import com.musicplatform.music.dto.SongResponse;
import com.musicplatform.music.entity.Song;
import com.musicplatform.music.exception.ErrorResponse;
import com.musicplatform.music.mapper.SongMapper;
import com.musicplatform.music.service.SongService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.musicplatform.music.dto.PageResponse;

@Tag(name = "Songs", description = "Operations on Songs")
@RestController
@RequestMapping("/api/songs")
public class SongController {

	private final SongService songService;

	public SongController(SongService songService) {
		this.songService = songService;
	}

	@Operation(summary = "Create a new song", description = "Creates an song with a name and audio ref. Returns 201 with the created song.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Song created"),
	    @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	    @ApiResponse(responseCode = "404", description = "Referenced album not found",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SongResponse create(@Valid @RequestBody SongRequest request) {
		 Song created = songService.create(request);
		 return SongMapper.toResponse(created);
	}

	@Operation(summary = "Get a song by ID", description = "Returns the song with the given ID, or 404 if not found.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Song returned"),
	    @ApiResponse(responseCode = "404", description = "Song not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{id}")
	public SongResponse getById(@PathVariable Long id) {
		 Song song = songService.getById(id);
		 return SongMapper.toResponse(song);
	}

	@Operation(summary = "List songs (paginated)")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Page of songs returned")
	})
	@GetMapping
	public PageResponse<SongResponse> getAll(Pageable pageable) {
		Page<Song> page = songService.getAll(pageable);

		List<SongResponse> content = page.getContent().stream().map(SongMapper::toResponse).toList();

		return new PageResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isFirst(), page.isLast());
	}

	@Operation(summary = "Update an existing song", description = "Updates an existing song. Returns the updated song or 404 if not found.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Song updated"),
	    @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	    @ApiResponse(responseCode = "404", description = "Song not found, or referenced album not found",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))	
	    })
	@PutMapping("/{id}")
	public SongResponse update(@PathVariable Long id,@Valid @RequestBody SongRequest request) {
		 Song updated = songService.update(id, request);
		 return SongMapper.toResponse(updated);
	}

	@Operation(summary = "Delete a song by ID")
	@ApiResponses({
		 @ApiResponse(responseCode = "204", description = "Song deleted"),
		 @ApiResponse(responseCode = "404", description = "Song not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		songService.delete(id);
	}
	
	@Operation(summary = "Add an artist to a song")
	@ApiResponses({
		 @ApiResponse(responseCode = "204", description = "Artist added"),
		 @ApiResponse(responseCode = "404", description = "Song not found, or artist not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/{songId}/artists/{artistId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addArtist(@PathVariable Long songId, @PathVariable Long artistId) {

		songService.addArtist(songId, artistId);
	}

	@Operation(summary = "Remove an artist from a song")
	@ApiResponses({
		 @ApiResponse(responseCode = "204", description = "Artist removed"),
		 @ApiResponse(responseCode = "404", description =  "Song not found, or artist not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/{songId}/artists/{artistId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeArtist(@PathVariable Long songId, @PathVariable Long artistId) {

		songService.removeArtist(songId, artistId);
	}
}