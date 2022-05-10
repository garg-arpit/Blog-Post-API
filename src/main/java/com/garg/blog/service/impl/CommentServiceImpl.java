package com.garg.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garg.blog.exceptions.ResourceNotFound;
import com.garg.blog.model.Comment;
import com.garg.blog.model.Post;
import com.garg.blog.model.User;
import com.garg.blog.payload.CommentDto;
import com.garg.blog.repositories.CommentRepo;
import com.garg.blog.repositories.PostRepo;
import com.garg.blog.repositories.UserRepo;
import com.garg.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, String username) {
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFound("User ", "email :" + username, 0));
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "post id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFound("Comment", "comment id", commentId));
		this.commentRepo.delete(comment);
	}

}
