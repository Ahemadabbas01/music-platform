package com.musicplatform.music.service;

import com.musicplatform.music.dto.RegisterRequest;
import com.musicplatform.music.dto.UserResponse;
import com.musicplatform.music.entity.User;

import jakarta.validation.Valid;

public interface AuthService {

	UserResponse register(RegisterRequest request);

}
