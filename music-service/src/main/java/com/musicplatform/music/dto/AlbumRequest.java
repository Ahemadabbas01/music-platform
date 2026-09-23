package com.musicplatform.music.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlbumRequest(@NotBlank String name, @Size(max = 500) String coverImageRef, @NotNull Long artistId) {

}
