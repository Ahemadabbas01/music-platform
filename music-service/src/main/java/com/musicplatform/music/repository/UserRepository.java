package com.musicplatform.music.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicplatform.music.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);
}
