package com.musicplatform.music.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GenreRequest(@NotBlank String name,@Size(max = 500) String description) {

}
