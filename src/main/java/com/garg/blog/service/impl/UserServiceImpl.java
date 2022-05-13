package com.garg.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.garg.blog.exceptions.ResourceNotFound;
import com.garg.blog.model.Role;
import com.garg.blog.model.User;
import com.garg.blog.payload.UserDto;
import com.garg.blog.repositories.RoleRepo;
import com.garg.blog.repositories.UserRepo;
import com.garg.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final int ROLE_USER = 502;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto craeteUser(UserDto userDto) {
		User user = this.userDtoToUser(userDto);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(ROLE_USER).get();
		user.getRoles().add(role);
		User savedUser = this.userRepo.save(user);
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User updatedUser = this.userRepo.save(user);
		return this.userToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "Id", userId));
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = this.userRepo.findAll();
		return userList.stream().map(user -> userToUserDto(user)).collect(Collectors.toList());
	}

	@Override
	public void deleteUserById(int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "Id", userId));
		this.userRepo.delete(user);
	}

	private User userDtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		return user;
	}

	private UserDto userToUserDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
