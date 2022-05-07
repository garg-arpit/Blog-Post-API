package com.garg.blog.service;

import java.util.List;

import com.garg.blog.payload.UserDto;

public interface UserService {

	public UserDto craeteUser(UserDto userDto);

	public UserDto updateUser(UserDto userDto, int userId);

	public UserDto getUserById(int userId);

	public List<UserDto> getAllUsers();

	void deleteUserById(int userId);
}
