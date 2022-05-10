package com.garg.blog.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garg.blog.payload.CommentDto;
import com.garg.blog.security.JwtTokenHelper;
import com.garg.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId, HttpServletRequest request) {
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtTokenHelper.extractUsername(jwt);
		} else {
			System.out.println("JWT Token does not start with Bearer");
		}
		CommentDto createdComment = this.commentService.createComment(commentDto, postId, username);
		return new ResponseEntity<CommentDto>(createdComment, HttpStatus.OK);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
