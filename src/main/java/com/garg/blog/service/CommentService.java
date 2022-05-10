package com.garg.blog.service;

import com.garg.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId, String username);

	void deleteComment(Integer commentId);
}
