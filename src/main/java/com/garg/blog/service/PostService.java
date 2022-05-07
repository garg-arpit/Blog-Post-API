package com.garg.blog.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.garg.blog.model.Post;
import com.garg.blog.payload.PostDto;
import com.garg.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, int userId, int categoryId, MultipartFile uploadedImage) throws IOException;

	PostDto updatePost(PostDto postDto, int postId);

	void deletePost(int postId);

	PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDirection);

	PostDto getPostById(int postId);

	List<PostDto> getPostByCategory(int categoryId);

	List<PostDto> getPostByUser(int userId);

	List<Post> searchPost(String keyword);
}
