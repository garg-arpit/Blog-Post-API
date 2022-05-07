package com.garg.blog.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseError {

	private long timestamp;
	private HttpStatus status;
	private List<String> errorMessage = new ArrayList<String>();
}
