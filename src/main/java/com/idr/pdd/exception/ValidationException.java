package com.idr.pdd.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidationException extends RuntimeException{

	public ValidationException(String message) {
		super(message);
	}
}
