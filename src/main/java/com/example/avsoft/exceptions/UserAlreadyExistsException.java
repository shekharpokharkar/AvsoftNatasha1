package com.example.avsoft.exceptions;

public class UserAlreadyExistsException extends RuntimeException  {
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
