package com.musicplatform.music.service;

import com.musicplatform.music.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
	
	 @Value("${jwt.secret}")
	 private String jwtSecret;
	 
	 @Value("${jwt.expiration-ms}")
	 private long jwtExpirationMs;
	 
	 public String generateToken(User user) {
		 SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		 return Jwts.builder()
	                .subject(user.getUsername())
	                .claim("role", user.getRole().name())
	                .issuedAt(new Date())
	                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
	                .signWith(key, SignatureAlgorithm.HS256)
	                .compact();
	    }

}
