package com.example.avsoft.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409), ex.getMessage());
		errorDetail.setProperty("description", "The email address is already registered.");
		return errorDetail;
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleSecurityException(Exception exception) {
		ProblemDetail errorDetail = null;

		// TODO send this stack trace to an observability tool
		exception.printStackTrace();

		if (exception instanceof BadCredentialsException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
			errorDetail.setProperty("description", "The username or password is incorrect");

			return errorDetail;
		}

		if (exception instanceof AccountStatusException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "The account is locked");
		}

		if (exception instanceof AccessDeniedException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "You are not authorized to access this resource");
		}

		if (exception instanceof SignatureException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "The JWT signature is invalid");
		}

		if (exception instanceof ExpiredJwtException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
			errorDetail.setProperty("description", "The JWT token has expired");
		}

		if (errorDetail == null) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
			errorDetail.setProperty("description", "Unknown internal server error.");
		}

		return errorDetail;
	}

	@ExceptionHandler(DocumentServiceException.class)
	public ResponseEntity<ProblemDetail> handleDocumentServiceException(DocumentServiceException e) {
		ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(e.status, e.getMessage());
		return new ResponseEntity<>(problemDetails, e.status);
	}

	@ExceptionHandler(value = VideoAlreadyExist.class)
	public ExceptionResponseDTO videoAlreadyExist(VideoAlreadyExist exception) {

		ExceptionResponseDTO dto = new ExceptionResponseDTO();
		dto.setDate(new Date());
		dto.setErrorClass(exception.getClass().toString());
		dto.setErrorMessage(exception.getMessage());

		return dto;

	}
	
	@ExceptionHandler(value = UserPaymentException.class)
	public ExceptionResponseDTO userPaymentException(UserPaymentException exception) {

		ExceptionResponseDTO dto = new ExceptionResponseDTO();
		
		dto.setDate(new Date());
		dto.setErrorClass(exception.getClass().toString());
		dto.setErrorMessage(exception.getMessage());

		return dto;

	}
}
