package com.interview.template.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestResponse<T> {

	private Boolean success;

	private T data;

	private String message;
}
