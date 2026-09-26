package com.musicplatform.music.controller;

import com.musicplatform.music.dto.AlbumRequest;
import com.musicplatform.music.dto.AlbumResponse;
import com.musicplatform.music.dto.PageResponse;
import com.musicplatform.music.entity.Album;
import com.musicplatform.music.exception.ErrorResponse;
import com.musicplatform.music.mapper.AlbumMapper;
import com.musicplatform.music.mapper.ArtistMapper;
import com.musicplatform.music.service.AlbumService;

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

@Tag(name = "Albums", description = "Operations on Albums")
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }
    
	@Operation(summary = "Create a new album", description = "Creates an album with a name. Returns 201 with the created album.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Album created"),
	    @ApiResponse(responseCode = "400", description = "Validation failed",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	    @ApiResponse(responseCode = "404", description = "Referenced artist not found",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse create(@Valid @RequestBody AlbumRequest request) {
         Album created = albumService.create(request);
         return AlbumMapper.toResponse(created);
    }

	@Operation(summary = "Get an album by ID", description = "Returns the album with the given ID, or 404 if not found.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Album returned"),
	    @ApiResponse(responseCode = "404", description = "Album not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
    @GetMapping("/{id}")
    public AlbumResponse getById(@PathVariable Long id) {
         Album album = albumService.getById(id);
         return AlbumMapper.toResponse(album);
    }

	@Operation(summary = "List albums (paginated)")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Page of album returned")
	})
	@GetMapping
	public PageResponse<AlbumResponse> getAll(Pageable pageable) {

		Page<Album> page = albumService.getAll(pageable);

		List<AlbumResponse> content = page.getContent().stream().map(AlbumMapper::toResponse).toList();
		return new PageResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isFirst(), page.isLast());
	}

	@Operation(summary = "Update an existing album", description = "Updates an existing album. Returns the updated album or 404 if not found.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Album updated"),
	    @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	    @ApiResponse(responseCode = "404", description = "Album not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PutMapping("/{id}")
	public AlbumResponse update(@PathVariable Long id, @Valid @RequestBody AlbumRequest request) {
		 Album updated = albumService.update(id, request);
		 
		 return  AlbumMapper.toResponse(updated);
	}
	
	@Operation(summary = "Delete an album by ID")
	@ApiResponses({
		 @ApiResponse(responseCode = "204", description = "Album deleted"),
		 @ApiResponse(responseCode = "404", description = "Album not found",
	                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        albumService.delete(id);
    }
}