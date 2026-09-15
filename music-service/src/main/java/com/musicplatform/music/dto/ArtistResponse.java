package com.musicplatform.music.dto;

import java.time.OffsetDateTime;

public class ArtistResponse {
	
	private Long id;
	private String name;
    private String details;
    private String country;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public ArtistResponse() {
    }
    
    public ArtistResponse(
            Long id,
            String name,
            String details,
            String country,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt) {

        this.id = id;
        this.name = name;
        this.details = details;
        this.country = country;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getCountry() {
        return country;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

}
