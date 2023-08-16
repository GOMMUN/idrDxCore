package com.idr.pdd.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageSendException extends RuntimeException{

	public MessageSendException(String message) {
		super(message);
	}
}
