package com.garg.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.garg.blog.exceptions.ResourceNotFound;
import com.garg.blog.model.User;
import com.garg.blog.repositories.UserRepo;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFound("User ", "email :" + username, 0));
		return user;
	}

}
