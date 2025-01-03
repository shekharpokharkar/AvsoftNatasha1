package com.example.avsoft.exceptions;

import org.springframework.http.HttpStatus;

public class DocumentServiceException extends RuntimeException{
    HttpStatus status;
    String message;
    public DocumentServiceException(String message, HttpStatus status) {
        super(message); this.message = message;
    }
}
