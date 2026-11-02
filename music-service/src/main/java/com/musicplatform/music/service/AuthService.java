package com.musicplatform.music.service;

import com.musicplatform.music.dto.AuthResponse;
import com.musicplatform.music.dto.LoginRequest;
import com.musicplatform.music.dto.RegisterRequest;
import com.musicplatform.music.dto.UserResponse;

public interface AuthService {

	UserResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);

}
