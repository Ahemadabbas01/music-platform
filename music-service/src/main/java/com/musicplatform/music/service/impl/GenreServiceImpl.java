package com.musicplatform.music.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.musicplatform.music.dto.GenreRequest;
import com.musicplatform.music.entity.Genre;
import com.musicplatform.music.exception.DuplicateResourceException;
import com.musicplatform.music.exception.ResourceNotFoundException;
import com.musicplatform.music.repository.GenreRepository;
import com.musicplatform.music.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService{

	 private final GenreRepository genreRepository;

	    public GenreServiceImpl(GenreRepository genreRepository) {
	        this.genreRepository = genreRepository;
	    }
	    
	@Override
	public Genre create(GenreRequest request) {
		 if (genreRepository.existsByName(request.name())) {
		        throw new DuplicateResourceException(
		                "Genre already exists: " + request.name());
		    }
		Genre genre = new Genre();
        genre.setName(request.name());
        genre.setDescription(request.description());
        return genreRepository.save(genre);
	}

    @Override
    public Genre getById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + id));
    }

    @Override
    public Page<Genre> search(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return genreRepository.findAll(pageable);
        }
        return genreRepository.findByNameStartingWithIgnoreCase(q.trim(), pageable);
    }

    @Override
    public Genre update(Long id, GenreRequest request) {
        Genre existing = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + id));
        
        if (genreRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new DuplicateResourceException(
                    "Genre already exists: " + request.name());
        }
        
        existing.setName(request.name());
        existing.setDescription(request.description());
        return genreRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + id));
        genreRepository.delete(genre);
    }

}
