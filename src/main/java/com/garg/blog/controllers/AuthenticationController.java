package com.garg.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garg.blog.payload.JwtAuthenticationRequest;
import com.garg.blog.payload.JwtAuthenticationResponse;
import com.garg.blog.payload.UserDto;
import com.garg.blog.security.JwtTokenHelper;
import com.garg.blog.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(
			@RequestBody final JwtAuthenticationRequest authenticationRequest) {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (final BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenHelper.generateToken(userDetails);
		final JwtAuthenticationResponse authenticationResponse = new JwtAuthenticationResponse();
		authenticationResponse.setToken(jwt);
		return ResponseEntity.ok(authenticationResponse);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<UserDto>(this.userService.craeteUser(userDto), HttpStatus.CREATED);
	}
}
