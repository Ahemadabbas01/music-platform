package com.musicplatform.music.dto;

import java.time.OffsetDateTime;

public record UserResponse(Long id,
	    String username,
	    String role,
	    OffsetDateTime createdAt) {

}
