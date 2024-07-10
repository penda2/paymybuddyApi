package com.paymybuddy.api.exception;

public class CustomException extends RuntimeException { // customizing exceptions for services

	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}
}
