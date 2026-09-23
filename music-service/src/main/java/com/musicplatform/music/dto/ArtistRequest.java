package com.musicplatform.music.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArtistRequest(
        @NotBlank String name,
        @Size(max = 2000) String details,
        @Size(max = 100) String country) {
}
