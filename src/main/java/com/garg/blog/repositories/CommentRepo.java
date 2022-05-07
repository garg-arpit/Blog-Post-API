package com.garg.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garg.blog.model.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
