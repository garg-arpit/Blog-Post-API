package com.garg.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garg.blog.payload.CategoryDto;
import com.garg.blog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<CategoryDto>(this.categoryService.createCategory(categoryDto), HttpStatus.CREATED);
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") int categoryId) {
		return new ResponseEntity<CategoryDto>(this.categoryService.updateCategory(categoryDto, categoryId),
				HttpStatus.OK);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("categoryId") int categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllUsers() {
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAllCategories(), HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getUserById(@PathVariable("categoryId") int categoryId) {
		return new ResponseEntity<CategoryDto>(this.categoryService.getCategoryById(categoryId), HttpStatus.OK);
	}
}
