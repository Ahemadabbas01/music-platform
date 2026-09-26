package com.musicplatform.music.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.musicplatform.music.dto.ArtistRequest;
import com.musicplatform.music.dto.ArtistResponse;
import com.musicplatform.music.dto.PageResponse;
import com.musicplatform.music.entity.Artist;
import com.musicplatform.music.mapper.ArtistMapper;
import com.musicplatform.music.service.ArtistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.musicplatform.music.exception.ErrorResponse;
import jakarta.validation.Valid;

@Tag(name = "Artists", description = "Operations on artists")
@RestController
@RequestMapping("/api/artists")
public class ArtistController {

	private final ArtistService artistService;

	public ArtistController(ArtistService artistService) {
		this.artistService = artistService;
	}

	@Operation(summary = "Create a new artist", description = "Creates an artist with a unique name. Returns 201 with the created artist.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Artist created"),
	    @ApiResponse(responseCode = "400", description = "Validation failed",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ArtistResponse create(@Valid @RequestBody ArtistRequest request) {
		Artist created = artistService.create(request);
		return ArtistMapper.toResponse(created);

	}


	@Operation(summary = "Get an artist by ID", description = "Returns the artist with the given ID, or 404 if not found.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Artist returned"),
	    @ApiResponse(responseCode = "404", description = "Artist not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{id}")
	public ArtistResponse getById(@PathVariable Long id) {
		Artist artistData = artistService.getById(id);
		return ArtistMapper.toResponse(artistData);
	}

	@Operation(summary = "List artists (paginated)")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Page of artists returned")
	})
	@GetMapping
	public PageResponse<ArtistResponse> getAll(Pageable pageable) {

		Page<Artist> page = artistService.getAll(pageable);

		List<ArtistResponse> content = page.getContent().stream().map(ArtistMapper::toResponse).toList();
		return new PageResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isFirst(), page.isLast());
	}
	

	@Operation(summary = "Update an existing artist", description = "Updates an existing artist. Returns the updated artist or 404 if not found.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Artist updated"),
	    @ApiResponse(responseCode = "400", description = "Validation failed",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	   @ApiResponse(responseCode = "404", description = "Artist not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PutMapping("/{id}")
	public ArtistResponse update(@PathVariable Long id, @Valid @RequestBody ArtistRequest request) {
		 Artist updated = artistService.update(id, request);
		 return ArtistMapper.toResponse(updated);
	}

	@Operation(summary = "Delete an artist by ID")
	@ApiResponses({
		 @ApiResponse(responseCode = "204", description = "Artist deleted"),
		 @ApiResponse(responseCode = "404", description = "Artist not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		artistService.delete(id);
	}
}