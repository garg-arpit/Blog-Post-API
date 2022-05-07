package com.garg.blog.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostDto {

	private int id;

	private String title;

	private String content;

	private String imageName;

	private Date addedDate;

	private byte[] image;

	private UserDto user;

	private CategoryDto category;

	private Set<CommentDto> comments = new HashSet<CommentDto>();
}
