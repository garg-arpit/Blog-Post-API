package com.garg.blog.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.garg.blog.exceptions.ResourceNotFound;
import com.garg.blog.model.Category;
import com.garg.blog.model.Post;
import com.garg.blog.model.User;
import com.garg.blog.payload.CategoryDto;
import com.garg.blog.payload.PostDto;
import com.garg.blog.payload.PostResponse;
import com.garg.blog.payload.UserDto;
import com.garg.blog.repositories.CategoryRepo;
import com.garg.blog.repositories.PostRepo;
import com.garg.blog.repositories.UserRepo;
import com.garg.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo UserRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId, MultipartFile uploadedImage) throws IOException {
		User user = this.UserRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName(uploadedImage.getOriginalFilename());
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		post.setUploadedImage(uploadedImage.getBytes());
		Post addedPost = this.postRepo.save(post);
		PostDto createdPost = this.modelMapper.map(addedPost, PostDto.class);
		createdPost.setCategoryDto(this.modelMapper.map(category, CategoryDto.class));
		createdPost.setUserDto(this.modelMapper.map(user, UserDto.class));
		return createdPost;
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "post id", postId));
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		Post updatedPost = this.postRepo.save(post);
		PostDto updatedPostDto = this.modelMapper.map(updatedPost, PostDto.class);
		updatedPostDto.setCategoryDto(this.modelMapper.map(post.getCategory(), CategoryDto.class));
		updatedPostDto.setUserDto(this.modelMapper.map(post.getUser(), UserDto.class));
		return updatedPostDto;
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "post id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = null;
		if (sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		PageRequest page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> postPage = this.postRepo.findAll(page);
		List<Post> postList = postPage.getContent();
		List<PostDto> postDtoList = postList.stream().map(post -> {
			PostDto postDto = this.modelMapper.map(post, PostDto.class);
			postDto.setUserDto(this.modelMapper.map(post.getUser(), UserDto.class));
			postDto.setCategoryDto(this.modelMapper.map(post.getCategory(), CategoryDto.class));
			return postDto;
		}).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setPostDtoContent(postDtoList);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElements(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages());
		postResponse.setLastPage(postPage.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "post id", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		postDto.setUserDto(this.modelMapper.map(post.getUser(), UserDto.class));
		postDto.setCategoryDto(this.modelMapper.map(post.getCategory(), CategoryDto.class));
		return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(int categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));
		List<Post> postList = this.postRepo.findByCategory(category);
		return postList.stream().map(post -> {
			PostDto postDto = this.modelMapper.map(post, PostDto.class);
			postDto.setUserDto(this.modelMapper.map(post.getUser(), UserDto.class));
			postDto.setCategoryDto(this.modelMapper.map(post.getCategory(), CategoryDto.class));
			return postDto;
		}).collect(Collectors.toList());
//		return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostByUser(int userId) {
		User user = this.UserRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));
		List<Post> postList = this.postRepo.findByUser(user);
		return postList.stream().map(post -> {
			PostDto postDto = this.modelMapper.map(post, PostDto.class);
			postDto.setUserDto(this.modelMapper.map(post.getUser(), UserDto.class));
			postDto.setCategoryDto(this.modelMapper.map(post.getCategory(), CategoryDto.class));
			return postDto;
		}).collect(Collectors.toList());
//		return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Post> searchPost(String keyword) {
		return null;
	}

}
