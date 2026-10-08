package com.musicplatform.music.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
@Table(name = "genre")
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false,unique = true,columnDefinition = "TEXT")
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = false)
	private OffsetDateTime createdAt;
	
	@Column(nullable = false)
	private OffsetDateTime updatedAt;
	
	public Genre () {}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}
	
	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public void setName(String name) {
		  this.name = name;
	}
	
	public void setDescription(String description) {
		this.description =  description;
	}
	
	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
    
}