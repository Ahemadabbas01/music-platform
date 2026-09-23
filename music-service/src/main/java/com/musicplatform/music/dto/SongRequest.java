package com.musicplatform.music.dto;

import jakarta.validation.constraints.NotBlank;

public record SongRequest(@NotBlank String title, @NotBlank String audioRef, Long albumId) {

}
