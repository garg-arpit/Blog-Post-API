package com.garg.blog.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

	private int id;

	@NotBlank(message = "Category Details must not empty!!")
	private String categoryDetail;

	private String categoryDescription;
}
