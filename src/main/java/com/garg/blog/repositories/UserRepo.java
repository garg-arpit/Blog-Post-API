package com.garg.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garg.blog.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}
