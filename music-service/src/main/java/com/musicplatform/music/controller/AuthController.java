package com.musicplatform.music.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.musicplatform.music.dto.RegisterRequest;
import com.musicplatform.music.dto.UserResponse;
import com.musicplatform.music.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "User registration and login")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	private AuthController(AuthService authService) {
		this.authService = authService;
	}

	@Operation(summary = "Register a new user")
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponse register(@Valid @RequestBody RegisterRequest request) {
		return authService.register(request);
	}
}
