package com.musicplatform.music.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFound(ResourceNotFoundException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		Map<String, String> fieldErrors = new HashMap<>();
		for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
			fieldErrors.putIfAbsent(fe.getField(), fe.getDefaultMessage());
		}

		ErrorResponse body = new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST.value(), "VALIDATION_FAILED", "Request validation failed", request.getRequestURI(), fieldErrors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

}
