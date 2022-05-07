package com.garg.blog.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.garg.blog.payload.PostDto;
import com.garg.blog.payload.PostResponse;
import com.garg.blog.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

//	@PostMapping("/users/{userId}/category/{categoryId}/posts")
//	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") int userId,
//			@PathVariable("categoryId") int categoryId) {
//		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
//		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
//	}

	@PostMapping(value = "/users/{userId}/category/{categoryId}/posts", consumes = {"multipart/form-data"})
	public ResponseEntity<PostDto> createPost(@ModelAttribute PostDto postDto, @PathVariable("userId") int userId,
			@PathVariable("categoryId") int categoryId,
			@RequestParam(value = "uploadedImage", required = false) MultipartFile uploadedImage) throws IOException {
		System.out.println(uploadedImage.getOriginalFilename());
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId, uploadedImage);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId) {
		return new ResponseEntity<PostDto>(this.postService.getPostById(postId), HttpStatus.OK);
	}

	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUsers(@PathVariable("userId") int userId) {
		List<PostDto> postList = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postList, HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") int categoryId) {
		return new ResponseEntity<List<PostDto>>(this.postService.getPostByCategory(categoryId), HttpStatus.OK);
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<HttpStatus> deletePost(@PathVariable("postId") int postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") int postId) {
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	}

}
