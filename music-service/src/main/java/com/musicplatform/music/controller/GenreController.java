package com.musicplatform.music.controller;

import com.musicplatform.music.dto.GenreRequest;
import com.musicplatform.music.dto.GenreResponse;
import com.musicplatform.music.dto.PageResponse;
import com.musicplatform.music.entity.Genre;
import com.musicplatform.music.exception.ErrorResponse;
import com.musicplatform.music.mapper.GenreMapper;
import com.musicplatform.music.service.GenreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Genres", description = "Operations on genres")
@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Operation(summary = "Create a new genre",
               description = "Creates a genre with a unique name. Returns 201 with the created genre.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Genre created"),
        @ApiResponse(responseCode = "400", description = "Validation failed",
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreResponse create(@Valid @RequestBody GenreRequest request) {
        Genre created = genreService.create(request);
        return GenreMapper.toResponse(created);
    }

    @Operation(summary = "Get a genre by ID",
               description = "Returns the genre with the given ID, or 404 if not found.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Genre returned"),
        @ApiResponse(responseCode = "404", description = "Genre not found",
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public GenreResponse getById(@PathVariable Long id) {
        Genre genre = genreService.getById(id);
        return GenreMapper.toResponse(genre);
    }

    @Operation(summary = "List genres (paginated, optional name search)",
               description = "Returns a page of genres. If 'q' is provided, "
                           + "filters genres whose name starts with the given prefix (case-insensitive).")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Page of genres returned")
    })
    @GetMapping
    public PageResponse<GenreResponse> getAll(
            @Parameter(description = "Optional prefix to filter genre names")
            @RequestParam(required = false) String q,
            Pageable pageable) {

        Page<Genre> page = genreService.search(q, pageable);
        List<GenreResponse> content = page.getContent().stream()
                .map(GenreMapper::toResponse)
                .toList();
        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }

    @Operation(summary = "Update an existing genre",
               description = "Updates an existing genre. Returns the updated genre or 404 if not found.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Genre updated"),
        @ApiResponse(responseCode = "400", description = "Validation failed",
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Genre not found",
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public GenreResponse update(@PathVariable Long id,
                                @Valid @RequestBody GenreRequest request) {
        Genre updated = genreService.update(id, request);
        return GenreMapper.toResponse(updated);
    }

    @Operation(summary = "Delete a genre by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Genre deleted"),
        @ApiResponse(responseCode = "404", description = "Genre not found",
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        genreService.delete(id);
    }
}