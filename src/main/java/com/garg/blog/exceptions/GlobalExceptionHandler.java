package com.garg.blog.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.garg.blog.payload.ResponseError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<ResponseError> resourceNotFoundExceptionHandler(ResourceNotFound ex) {
		ResponseError error = new ResponseError();
		error.setTimestamp(System.currentTimeMillis());
		error.setStatus(HttpStatus.NOT_FOUND);
		List<String> errorMessage = List.of(ex.getMessage());
		error.setErrorMessage(errorMessage);
		return new ResponseEntity<ResponseError>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ResponseError error = new ResponseError();
		error.setTimestamp(System.currentTimeMillis());
		error.setStatus(status);
		List<String> errorMessage = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage())
				.collect(Collectors.toList());
		error.setErrorMessage(errorMessage);
		return new ResponseEntity<Object>(error, status);
	}
}
