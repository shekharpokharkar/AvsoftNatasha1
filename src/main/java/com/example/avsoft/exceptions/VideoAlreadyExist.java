package com.example.avsoft.exceptions;

public class VideoAlreadyExist extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VideoAlreadyExist(String message) {
		super(message);
	}

}
