package com.idr.pdd.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidationException extends Exception{

	public ValidationException(String message) {
		super(message);
	}
}
