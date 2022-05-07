package com.garg.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garg.blog.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
