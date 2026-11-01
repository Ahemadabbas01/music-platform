package com.musicplatform.music.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.musicplatform.music.dto.RegisterRequest;
import com.musicplatform.music.dto.UserResponse;
import com.musicplatform.music.entity.Role;
import com.musicplatform.music.entity.User;
import com.musicplatform.music.exception.DuplicateResourceException;
import com.musicplatform.music.repository.UserRepository;
import com.musicplatform.music.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponse register(RegisterRequest request) {

		if (userRepository.existsByUsername(request.username())) {
			throw new DuplicateResourceException("User Name already exists: " + request.username());
		}

		User user = new User();
		user.setUsername(request.username());
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setRole(Role.USER);

		User savedUser = userRepository.save(user);

		return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getRole().name(),
				savedUser.getCreatedAt());
	}

}
