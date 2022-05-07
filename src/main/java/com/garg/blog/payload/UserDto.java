package com.garg.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {

	private int id;

	@NotBlank(message = "User Name must not be blank.")
	@Size(min = 4, message = "UserName must be min of 4 characters!!")
	private String name;

	@Email(message = "Invalid Email address.")
	private String email;

	@JsonIgnore
	@NotBlank(message = "Password must not be blank.")
	@Size(min = 4, message = "Password must be min of 4 characters!!")
	private String password;

	@NotBlank(message = "About must not be blank.")
	@Size(min = 10, message = "About must be min of 10 characters!!")
	private String about;

	public UserDto() {
		super();
	}

	public UserDto(int id, String name, String email, String password, String about) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

}
